package com.dtt.organization.service.impl;

import com.dtt.organization.dto.*;
import com.dtt.organization.model.*;
import com.dtt.organization.repository.*;
import com.dtt.organization.util.*;

import com.dtt.organization.service.iface.OrganizationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
    @Transactional
    public class OrganizationServiceImpl implements OrganizationService {

    private static final String CLASS = "OrganizationServiceImpl";
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);


    @Value("${enable.postpaid}")
    boolean enablePostPaid;

    @Value("${register.organisation}")
    String registerOrganisation;


    @Value("${portal.url}")
    private String portalUrl;

    @Value("${portal.name}")
    private String portalName;

    @Autowired
    APIRequestHandler apiRequestHandler;

    @Autowired
    OrganisationCategoryRepo organisationCategoryRepo;

    @Autowired
    MetaDocumentRepository metaDocumentRepository;


    ObjectMapper objectMapper = new ObjectMapper();

    private final OrganizationRepository organizationRepository;
    private final AuditorRepository auditorRepository;
    private final SpocRepository spocRepository;
    private final TrustedUsersRepository trustedUsersRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final OrganisationViewIface organisationViewIface;

    private final SoftwareRepository softwareRepository;
    private final OrganizationDocumentRepository organizationDocumentRepository;




    public OrganizationServiceImpl(
            OrganizationRepository establishmentRepository,
            AuditorRepository auditorRepository,
            SpocRepository spocRepository,

            TrustedUsersRepository trustedUsersRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder,
            OrganisationViewIface organisationViewIface,

            SoftwareRepository softwareRepository,
      OrganizationDocumentRepository organizationDocumentRepository) {

        this.organizationRepository = establishmentRepository;
        this.auditorRepository = auditorRepository;
        this.spocRepository = spocRepository;

        this.trustedUsersRepository = trustedUsersRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;

        this.softwareRepository = softwareRepository;
        this.organisationViewIface = organisationViewIface;
        this.organizationDocumentRepository = organizationDocumentRepository;
    }


    @Value("${organisation.exists}")
    String organisationExists;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse save(OrganizationOnboardingDTO dto, Map<String, List<MultipartFile>> documents) {
        logger.info("{} save() organization onboarding", CLASS);

        try {
            String regNo = dto.getOrganization().getRegNo();
            String taxNumber = dto.getOrganization().getTaxNumber();
            String orgName = dto.getOrganization().getOrgName();


            ApiResponse uniqueCheck = checkUniqueness(orgName, regNo, taxNumber);
            if (uniqueCheck != null) return uniqueCheck;


            List<MetaDocumentEntity> metaDocs = metaDocumentRepository.findAll();


            OrganizationEntity organizationEntity = new OrganizationEntity();
            organizationEntity.setOrgName(orgName);
            organizationEntity.setRegNo(regNo);
            organizationEntity.setOrgType(dto.getOrganization().getOrgType());
            organizationEntity.setTaxNumber(taxNumber);
            organizationEntity.setAddress(dto.getOrganization().getAddress());
            organizationEntity.setOrgEmail(dto.getOrganization().getOrgEmail());
            organizationEntity.setStatus("PENDING");
            organizationEntity.setCreatedOn(AppUtil.getDate());
            organizationEntity.setUpdatedOn(AppUtil.getDate());
            organizationEntity = organizationRepository.save(organizationEntity);

            Long orgId = organizationEntity.getId();


            for (MetaDocumentEntity meta : metaDocs) {
                List<MultipartFile> files = (documents != null) ? documents.get(meta.getDocumentLabel()) : null;


                if (meta.isMandatory() && (files == null || files.isEmpty() || files.get(0).isEmpty())) {
                    throw new ValidationException(meta.getDocumentLabel() + " is mandatory");
                }

                if (files != null) {
                    for (MultipartFile file : files) {
                        if (file != null && !file.isEmpty()) {
                            validateDocument(file, meta);
                            saveDocumentEntity(orgId, meta.getId(), file);
                        }
                    }
                }
            }


            saveSpoc(orgId, dto.getSpocDetails());


            if (dto.getAuditorDetails() != null) {
                saveAuditor(orgId, dto.getAuditorDetails());
            }

            notifyAdmin(orgName);

            return new ApiResponse<>(true, "The organization details have been submitted successfully and are awaiting approval.", null);

        } catch (ValidationException ve) {
            logger.warn("Validation error during onboarding: {}", ve.getMessage());
            return new ApiResponse(false, ve.getMessage(), null);
        } catch (Exception e) {
            logger.error("Error while onboarding organization", e);
            return new ApiResponse(false, "Something went wrong during submission", null);
        }
    }

    private void validateDocument(MultipartFile file, MetaDocumentEntity meta) {

        long maxSizeInBytes = meta.getDocumentSizeKb() * 1024;
        if (file.getSize() > maxSizeInBytes) {
            throw new ValidationException(meta.getDocumentLabel() + " size exceeds " + meta.getDocumentSizeKb() + " KB");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            throw new ValidationException("Invalid file name for " + meta.getDocumentLabel());
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String allowedTypes = meta.getDocumentType().toLowerCase(); // e.g., "pdf,jpg"

        if (!allowedTypes.contains(extension)) {
            throw new ValidationException(meta.getDocumentLabel() + " must be of type: " + meta.getDocumentType());
        }
    }


    private void saveDocumentEntity(Long orgId, Long metaDocumentId, MultipartFile file) throws IOException {
        OrganizationDocumentEntity doc = new OrganizationDocumentEntity();
        doc.setOrgId(orgId);
        doc.setMetaDocumentId(metaDocumentId);
        doc.setDocumentName(file.getOriginalFilename());
        doc.setContentType(file.getContentType());
        doc.setDocumentData(Base64.getEncoder().encodeToString(file.getBytes()));
        doc.setCreatedOn(AppUtil.getDate());
        organizationDocumentRepository.save(doc);
    }

    private ApiResponse checkUniqueness(String orgName, String regNo, String taxNumber) {

        String url = organisationExists + orgName;
        ApiResponse res = apiRequestHandler.handleApiRequest(url, HttpMethod.GET, null);
        if (res != null && res.isSuccess()) {
            return new ApiResponse(false, "Organization with this name already exists", null);
        }

        if (regNo != null && organizationRepository.existsByRegNo(regNo)) {
            return new ApiResponse(false, "Registration number already exists", null);
        }
        if (taxNumber != null && organizationRepository.existsByTaxNumber(taxNumber)) {
            return new ApiResponse(false, "Tax number already exists", null);
        }
        if (organizationRepository.existsByOrgNameIgnoreCase(orgName)) {
            return new ApiResponse(false, "Organization Name already exists", null);
        }
        return null;
    }

    private void saveSpoc(Long orgId, SpocDetailsDTO spocDto) {
        SpocEntity spoc = new SpocEntity();
        spoc.setOrgDetailsId(orgId);
        spoc.setSpocName(spocDto.getSpocName());
        spoc.setSpocOfficalEmail(spocDto.getSpocOfficalEmail());
        spoc.setSpocDocumentNumber(spocDto.getSpocDocumentNumber());
        spoc.setCreatedOn(AppUtil.getDate());
        spoc.setUpdatedOn(AppUtil.getDate());
        spocRepository.save(spoc);
    }

    private void saveAuditor(Long orgId, AuditorDetailsDTO auditorDto) {
        AuditorEntity auditor = new AuditorEntity();
        auditor.setOrgDetailsId(orgId);
        auditor.setAuditorName(auditorDto.getAuditorName());
        auditor.setAuditorDocumentNumber(auditorDto.getAuditorDocumentNumber());
        auditor.setAuditorOfficialEmail(auditorDto.getAuditorOfficalEmail());
        auditor.setCreatedOn(AppUtil.getDate());
        auditor.setUpdatedOn(AppUtil.getDate());
        auditorRepository.save(auditor);
    }

    private void notifyAdmin(String orgName) {
        String subject = "Onboarding Approval Request submitted";

        String htmlContent =
                "<html>" +
                        "<body style='font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #000;'>" +

                        "<p>Greetings!</p>" +

                        "<p>" +
                        "Administrator,<br/>" +
                        "The SPOC has submitted an onboarding request for the organization " +
                        "<strong>" + orgName + "</strong>." +
                        "</p>" +

                        "<p>Kindly do the needful.</p>" +

                        "<p>Thanks!</p>" +

                        "</body>" +
                        "</html>";
        ApiResponse res = emailService.getAdminEmailList();
        if (res.isSuccess()) {
            List<String> adminEmails = (List<String>) res.getResult();
            if (!adminEmails.isEmpty()) {
                emailService.sendEmailForAdmin(adminEmails, htmlContent, subject);
            }
        }
    }

    @Override
    public ApiResponse approveOrRejectOrg(String status,Long id) {
        logger.info("{}  approve Or Reject Org{} ", CLASS, id);
        try {

            OrganizationEntity organizationEntity = organizationRepository.findById(id).orElseThrow(() ->
                    new ValidationException("Organization not found"));
            SpocEntity spoc = spocRepository.findByOrgDetailsId(id).orElseThrow(() -> new ValidationException("SPOC not found for this Organization"));


            if (status.equals("APPROVED")) {
                logger.info("{} org approved ::::" ,CLASS);
                if ("APPROVED".equalsIgnoreCase(organizationEntity.getStatus())) {
                    return new ApiResponse<>(false, "Organization already approved", null);
                }


                RegisterOrganizationDTO registerOrganizationDTO = new RegisterOrganizationDTO();

                HttpHeaders headers = new HttpHeaders();

                List<Integer> tempId = new ArrayList<>();
                List<String> checkBox = new ArrayList<>();
                tempId.add(1);
                tempId.add(5);
                registerOrganizationDTO.setOrganizationName(organizationEntity.getOrgName());
                registerOrganizationDTO.setSpocUgpassEmail(spoc.getSpocOfficalEmail());
                registerOrganizationDTO.setManageByAdmin(false);

                registerOrganizationDTO.setTaxNo(organizationEntity.getTaxNumber());
                registerOrganizationDTO.setCorporateOfficeAddress(organizationEntity.getAddress());
                registerOrganizationDTO.setTemplateId(tempId);
                registerOrganizationDTO.setDocumentListCheckbox(checkBox);


                registerOrganizationDTO.setOrganizationEmail(spoc.getSpocOfficalEmail());

                if(enablePostPaid){
                    registerOrganizationDTO.setEnablePostPaidOption(true);
                }
                else{
                    registerOrganizationDTO.setEnablePostPaidOption(false);
                }



                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> reqEntity = new HttpEntity<>(registerOrganizationDTO, headers);





                String url = registerOrganisation;
                logger.info("{} URL CALLING ::::{}" ,CLASS,url);
                ApiResponse res = apiRequestHandler.handleApiRequest(url, HttpMethod.POST, reqEntity);


                if (!res.isSuccess()) {
                    return new ApiResponse(false, res.getMessage(), null);
                }



                String response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res.getResult());

                RegisterOrganizationDTO responseDto = objectMapper.readValue(response, RegisterOrganizationDTO.class);


                organizationEntity.setStatus("ACTIVE");
                organizationEntity.setOuid(responseDto.getOrganizationUid());
                organizationEntity.setUpdatedOn(AppUtil.getDate());

                organizationRepository.save(organizationEntity);



                String body =
                        "<html>" +
                                "<body style='font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #000;'>" +

                                "<p>Dear " + spoc.getSpocName() + ",</p>" +

                                "<p>Your organization has been approved on the <strong>" + portalName + "</strong>.</p>" +

                                "<p>" +
                                "<strong>Organization Name:</strong> " + organizationEntity.getOrgName() +
                                "</p>" +

                                "<p>You can now access the <strong>" + portalName + "</strong> using your existing login credentials.</p>" +

                                "<p>" +
                                "<strong>Portal Link:</strong><br/>" +
                                "<a href='" + portalUrl + "' target='_blank'>" + portalUrl + "</a>" +
                                "</p>" +

                                "<p>Regards,<br/>" +
                                "<strong> Admin</strong></p>" +

                                "<p style='font-size: 10px; font-style: italic; color: gray;'>" +
                                "* This is an automated email from <strong>" + portalName + "</strong>. " +
                                "Please contact the administrator if you have any questions regarding this email." +
                                "</p>" +


                                "</body>" +
                                "</html>";


                emailService.sendEmail(spoc.getSpocOfficalEmail(),  body,"Your Organization is Approved");




                return new ApiResponse<>(true, "Organization approved successfully", null);
            } else if (status.equals("REJECTED")) {
                logger.info("{} org rejected ::::" ,CLASS);

                if ("REJECTED".equalsIgnoreCase(organizationEntity.getStatus())) {
                    return new ApiResponse<>(false, "Organization already rejected", null);
                }
                organizationEntity.setStatus("REJECTED");
                organizationEntity.setUpdatedOn(AppUtil.getDate());

                organizationRepository.save(organizationEntity);
                String body =
                        "<html>" +
                                "<body style='font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #000;'>" +

                                "<p>Dear " + spoc.getSpocName() + ",</p>" +

                                "<p>" +
                                "We regret to inform you that your organisation " +
                                "<strong>\"" + organizationEntity.getOrgName() + "\"</strong> " +
                                "has been rejected." +
                                "</p>" +

                                "<p>Regards,<br/>" +
                                "Admin</p>" +


                                "<p style='font-size: 10px; font-style: italic; color: gray;'>" +
                                "* This is an automated email from <strong>" + portalName + "</strong>. " +
                                "Please contact the administrator if you have any questions regarding this email." +
                                "</p>" +


                                "</body>" +
                                "</html>";


                emailService.sendEmail(spoc.getSpocOfficalEmail(),  body,"Organisation Rejected");


                return new ApiResponse<>(true, "Organization rejected ", null);
            } else {
                return new ApiResponse<>(false, "send proper status", null);
            }
        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);

        }
    }


    @Override
    public ApiResponse  getAllOrganizations() {

        logger.info("{} get All Organizations",CLASS);
        try{

        List<OrganizationEntity> organizationEntities = organizationRepository.findAllOrgs();


        List<GetAllOrganizationtDTO> organizationtDTOS = new ArrayList<>();


        for (OrganizationEntity establishment : organizationEntities) {

            GetAllOrganizationtDTO establishmentDTO = new GetAllOrganizationtDTO();
            establishmentDTO.setOrgName(establishment.getOrgName());
            establishmentDTO.setOrgDetailsId(establishment.getId());
            establishmentDTO.setRegNo(establishment.getRegNo());
            establishmentDTO.setOrgType(establishment.getOrgType());
            establishmentDTO.setStatus(establishment.getStatus());
            establishmentDTO.setTaxNumber(establishment.getTaxNumber());
            establishmentDTO.setAddress(establishment.getAddress());
            establishmentDTO.setOrgEmail(establishment.getOrgEmail());
            Optional<SpocEntity> spocOpt =
                    spocRepository.findByOrgDetailsId(establishment.getId());

            if (spocOpt.isPresent()) {
                establishmentDTO.setSpocName(spocOpt.get().getSpocName());
                establishmentDTO.setSpocOfficalEmail(spocOpt.get().getSpocOfficalEmail());
            }

            organizationtDTOS.add(establishmentDTO);
        }


        return new ApiResponse<>(true, "Organizations fetched successfully", organizationtDTOS);
        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);

        }
    }








    @Override
    public ApiResponse getOrganizationDetailsById(Long id) {
        logger.info("{} get organization by id {}",CLASS,id);
        try {

            OrganizationEntity organizationEntity = organizationRepository.findById(id).orElse(null);

            if (organizationEntity == null) {
                return new ApiResponse<>(false, "Organization not found", null);
            }


            Optional<SpocEntity> spocEntity = spocRepository.findByOrgDetailsId(organizationEntity.getId());

            logger.info("{} spoc details {}",CLASS,spocEntity);

            Optional<AuditorEntity> auditorEntity = auditorRepository.findByOrgDetailsId(organizationEntity.getId());

            OrganizationDetailsResponseDTO response = new OrganizationDetailsResponseDTO();


            response.setOuid(organizationEntity.getOuid());
            response.setOrgName(organizationEntity.getOrgName());
            response.setOrgNo(organizationEntity.getRegNo());

            response.setOrgType(organizationEntity.getOrgType());

            response.setStatus(organizationEntity.getStatus());
            response.setTaxNumber(organizationEntity.getTaxNumber());
            response.setOrgDetailsId(organizationEntity.getId());
            response.setCreatedOn(AppUtil.formatDate(organizationEntity.getUpdatedOn()));
            response.setAddress(organizationEntity.getAddress());
            response.setOrgEmail(organizationEntity.getOrgEmail());


            if (spocEntity.isPresent()) {
                response.setSpocName(spocEntity.get().getSpocName());
                response.setSpocOfficialEmail(spocEntity.get().getSpocOfficalEmail());
                response.setSpocDocumentNumber(spocEntity.get().getSpocDocumentNumber());
            }


            if (auditorEntity.isPresent()) {
                response.setAuditorName(auditorEntity.get().getAuditorName());
                response.setAuditorOfficialEmail(
                        auditorEntity.get().getAuditorOfficialEmail());
                response.setAuditorDocumentNumber(
                        auditorEntity.get().getAuditorDocumentNumber());
            }

            List<OrganizationDocumentEntity> orgDocuments =
                    organizationDocumentRepository.findByOrgId(organizationEntity.getId());

            List<DocumentResponseDTO> documentDtos = new ArrayList<>();

            for (OrganizationDocumentEntity orgDoc : orgDocuments) {

                Optional<MetaDocumentEntity> metaOpt = metaDocumentRepository.findById(orgDoc.getMetaDocumentId());

                if (metaOpt.isPresent()) {
                    MetaDocumentEntity meta = metaOpt.get();

                    DocumentResponseDTO docDto = new DocumentResponseDTO();
                    docDto.setDocumentName(meta.getDocumentName());
                    docDto.setDocumentType(meta.getDocumentType());


                    docDto.setDocumentData(portalUrl+"/api/public/download/document/by/id/"+organizationEntity.getId()+"/"+meta.getId()+"/" +meta.getDocumentType());

                    documentDtos.add(docDto);
                }
            }

            response.setDocuments(documentDtos);




            return new ApiResponse<>(true, "Organization details fetched successfully", response);

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse<>(false, "Something went wrong", null);
        }
    }
@Transactional
    @Override
    public ApiResponse<Page<SpocOrganizationResponseDTO>> getOrganizationsBySpocEmail(String email, int page, int size) {

        try {
             ApiResponse res = syncDataFromAdmin(email);



            List<SpocEntity> spocList = spocRepository.findAllBySpocOfficalEmail(email);
            if (spocList.isEmpty()) {
                return new ApiResponse<>(false, "No organizations found", Page.empty());
            }
            List<Long> orgIds = spocList.stream()
                    .map(SpocEntity::getOrgDetailsId)
                    .distinct()
                    .toList();

            Pageable pageable = PageRequest.of(
                    page,
                    size,
                    Sort.by("createdOn").descending()
            );

            Page<OrganizationEntity> orgPage = organizationRepository.findByIdIn(orgIds, pageable);

            Page<SpocOrganizationResponseDTO> dtoPage =
                    orgPage.map(org -> {
                        SpocOrganizationResponseDTO dto =
                                new SpocOrganizationResponseDTO();

                        dto.setOrganizationId(org.getId());
                        dto.setOrganizationName(org.getOrgName());
                        dto.setRegNo(org.getRegNo());
                        dto.setStatus(org.getStatus());
                        dto.setCreatedOn(AppUtil.formatDate(org.getCreatedOn()));
                        dto.setOrgType(org.getOrgType());

                        return dto;
                    });

            return new ApiResponse<>(true, "Organizations fetched successfully", dtoPage);

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse<>(false, "Something went wrong", null);
        }
    }


@Override
public ApiResponse getDashboardDetails(String spocEmail) {

    try {

         ApiResponse res1 = syncDataFromAdmin(spocEmail);
         if(!res1.isSuccess()){
             return new ApiResponse<>(false, "Something went wrong", null);
         }



        DashboardResponseDto dto = new DashboardResponseDto();
        List<OrganizationEntity> orgs = organizationRepository.getAllOrgsBySpocEmail(spocEmail);

        dto.setOrgs(organizationRepository.countActiveOrganizationsBySpocEmail(spocEmail));
        dto.setTotalApplications(organizationRepository.countApplicationsBySpocEmail(spocEmail));
        long pendingApplications = 0;
        long approvedOrganizations = 0;

        for (OrganizationEntity org : orgs) {


            if ("PENDING".equalsIgnoreCase(org.getStatus())) {
                pendingApplications++;
            }
            else if ("ACTIVE".equalsIgnoreCase(org.getStatus())) {
                approvedOrganizations++;
            }
        }

        dto.setPendingApplications(pendingApplications);
        dto.setTotalOrganizations(approvedOrganizations);

        return new ApiResponse(true, "Fetched", dto);

    } catch (Exception e) {
        logger.error("Unexpected exception", e);
        return new ApiResponse(false, "Failed to fetch dashboard data", null);
    }
}

    @Transactional
    public ApiResponse syncDataFromAdmin(String email) {
        try {
            if (email == null || email.isEmpty()) {
                return new ApiResponse(false,"Email cannot be null",null);
            }

            List<OrganizationEntity> formsInSSP = organizationRepository.OrganizationsBySpocEmail(email);
           
            List<OrganisationView> formsInView = organisationViewIface.getDetailsByEmail(email);
          

            updateChangedSpocs(formsInSSP, formsInView);
            createMissingOrganisations(email, formsInSSP, formsInView);




            return new ApiResponse(true,"Fetched",null);

        } catch (Exception e) {

            return new ApiResponse(false,"Something went wrong",null);
        }
    }


    private void updateChangedSpocs(List<OrganizationEntity> formsInSSP,
                                    List<OrganisationView> formsInView) throws JsonProcessingException {

        Map<String, String> orgUgPassEmailMap = formsInView.stream()
                .collect(Collectors.toMap(OrganisationView::getOuid, OrganisationView::getSpocUgpassEmail, (e1, e2) -> e1));



        for (OrganizationEntity sspForm : formsInSSP) {
            String ouid = sspForm.getOuid();
            String viewEmail = orgUgPassEmailMap.get(ouid);


           Optional<SpocEntity> spocEntity = spocRepository.findByOrgDetailsId(sspForm.getId());
            String sspEmail = spocEntity.get().getSpocOfficalEmail();

            if (sspEmail != null && viewEmail == null && !sspEmail.equals(viewEmail)) {

                handleSpocMismatch(sspForm, ouid);
            }
        }
    }


    private void handleSpocMismatch(OrganizationEntity sspForm, String ouid)
            throws JsonProcessingException {

        Optional<OrganizationEntity> form = organizationRepository.findById(sspForm.getId());
        if (form == null) {
            return;
        }

        OrganisationView details = organisationViewIface.getESealDetails(ouid);

        updateSpocDetailsWithoutDevice(form, details);

    }


    private void updateSpocDetailsWithoutDevice(Optional<OrganizationEntity> form, OrganisationView details) {


        spocRepository.findByOrgDetailsId(form.get().getId())
                .ifPresent(spoc -> {


                    spoc.setSpocOfficalEmail(details.getSpocUgpassEmail());

                   spoc.setSpocName(null);
                   spoc.setSpocDocumentNumber(null);

                   spoc.setUpdatedOn(AppUtil.getDate());
                    spocRepository.save(spoc);
                });
    }




    private void createMissingOrganisations(String email,
                                            List<OrganizationEntity> formsInSSP,
                                            List<OrganisationView> formsInView) throws JsonProcessingException {



        for (OrganisationView viewForm : formsInView) {
            boolean exists = formsInSSP.stream().anyMatch(f -> f.getOuid().equals(viewForm.getOuid()));
            if (!exists) {
                createNewOrganisation(viewForm, email);
            }
        }
    }

    private void createNewOrganisation(OrganisationView viewForm,
                                       String email) {

        OrganizationEntity newForm = new OrganizationEntity();
        newForm.setOrgName(viewForm.getOrgName());
        newForm.setRegNo(viewForm.getUniqueRegdNo());
        newForm.setOuid(viewForm.getOuid());
        newForm.setTaxNumber(viewForm.getTaxNo());
        newForm.setAddress(viewForm.getCorporateOfficeAddress());
        newForm.setOrgEmail(viewForm.getOrganizationEmail());

        newForm.setOrgType("government");
        newForm.setStatus("ACTIVE");
        newForm.setOrgAddedByAdmin(true);
        newForm.setCreatedOn(viewForm.getCreatedDate());
        newForm.setUpdatedOn(viewForm.getUpdatedDate());


        OrganizationEntity saved = organizationRepository.save(newForm);

        AuditorEntity auditor = new AuditorEntity();
        auditor.setOrgDetailsId(saved.getId());
        auditorRepository.save(auditor);



        SpocEntity spoc = new SpocEntity();
        spoc.setOrgDetailsId(saved.getId());
        spoc.setSpocOfficalEmail(email);
        spoc.setSpocDocumentNumber(null);
        spoc.setSpocName(null);

        spoc.setCreatedOn(viewForm.getCreatedDate());
        spoc.setUpdatedOn(viewForm.getUpdatedDate());
        spocRepository.save(spoc);
    }


    @Override
    public ApiResponse getAllOrganizationApprovalDetails() {
        try {
            List<Object[]> results = organizationRepository.findAllApprovedOrganizations();

            List<Map<String, Object>> responseList = new ArrayList<>();

            for (Object[] row : results) {
                Map<String, Object> orgData = new HashMap<>();
                orgData.put("org_uid", row[0]);
                orgData.put("org_status", row[1]);
                orgData.put("org_category", row[2]);
                responseList.add(orgData);
            }
            return new ApiResponse(true,"Fetched Successfully", responseList);

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"something went wrong",null);
        }
    }


    @Override
    public ApiResponse getOrgCategoryandidByOrgid(String orgId) {
        try{
            if(orgId == null || orgId.isEmpty()){
                return new ApiResponse(false,"Org ID cannot be null",null);
            }

            OrganizationEntity organisationOnboardingForms = organizationRepository.allFormByOrgUid(orgId);



            if(organisationOnboardingForms ==null){
                return new ApiResponse(false,"Organisation details not found",null);
            }

            Optional<OrganisationCategories> organisationCategories = organisationCategoryRepo.findByCategoryName(organisationOnboardingForms.getOrgType());

            GetOrgIDByOuidResponseDto organisationOnboardingFormsDTO = new GetOrgIDByOuidResponseDto();
           organisationOnboardingFormsDTO.setOrgName(organisationOnboardingForms.getOrgName());
           organisationOnboardingFormsDTO.setOuid(organisationOnboardingForms.getOuid());
            organisationOnboardingFormsDTO.setCategoryName(organisationOnboardingForms.getOrgType());
            organisationOnboardingFormsDTO.setCategoryId(organisationCategories.get().getId());
            organisationOnboardingFormsDTO.setCategoryDisplayName(organisationCategories.get().getLabelName());



            return new ApiResponse(true,"Organisation category name and organisation category id fetched successfully",organisationOnboardingFormsDTO);




        }catch(Exception e){
            logger.error("Unexpected exception", e);

            return new ApiResponse(false,"Somthing went wrong",null);
        }
    }
    @Override
    public ApiResponse getRecentOrganizationBySpocEmail(String spocEmail) {

        try {
            if (spocEmail == null || spocEmail.isEmpty()) {
                return new ApiResponse(false, "SPOC email cannot be null", null);
            }

            Optional<SpocEntity> spocOpt =
                    spocRepository.findTopBySpocOfficalEmailOrderByCreatedOnDesc(spocEmail);

            if (!spocOpt.isPresent()) {
                return new ApiResponse(false, "SPOC details not found", null);
            }

            SpocEntity spoc = spocOpt.get();


            Optional<OrganizationEntity> orgOpt =
                    organizationRepository.findById(spoc.getOrgDetailsId());

            if (!orgOpt.isPresent()) {
                return new ApiResponse(false, "Organisation details not found", null);
            }

            OrganizationEntity org = orgOpt.get();


            OrganizationRecentDTO dto = new OrganizationRecentDTO();
            dto.setOrgName(org.getOrgName());
            String orgType = org.getOrgType();
            if (orgType != null && !orgType.isEmpty()) {
                dto.setOrgType(
                        orgType.substring(0, 1).toUpperCase() + orgType.substring(1).toLowerCase()
                );
            }
            dto.setStatus(org.getStatus());
            String createdOn = org.getCreatedOn();
            if (createdOn != null && createdOn.length() >= 10) {
                dto.setCreatedOn(createdOn.substring(0, 10));
            }
            dto.setTaxNumber(org.getTaxNumber());
            dto.setRegNo(org.getRegNo());

            return new ApiResponse(
                    true,
                    "Recent organisation details fetched successfully",
                    dto
            );

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Something went wrong", null);
        }
    }



    @Override
    public ResponseEntity<Resource> downloadDocument(Long orgDetailsId, Long documentId, String documentType) {

        OrganizationDocumentEntity doc =
                organizationDocumentRepository.findByOrgIdAndMetaDocumentId(orgDetailsId, documentId);

        byte[] fileBytes = Base64.getDecoder().decode(doc.getDocumentData());

        ByteArrayResource resource = new ByteArrayResource(fileBytes);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getContentType()))
                .contentLength(fileBytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + doc.getDocumentName() + "\"")
                .body((Resource) resource);
    }



}
