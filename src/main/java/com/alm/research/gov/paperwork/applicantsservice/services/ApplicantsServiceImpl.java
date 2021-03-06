package com.alm.research.gov.paperwork.applicantsservice.services;

import com.alm.research.gov.paperwork.applicantsservice.models.applicant.Applicant;
import com.alm.research.gov.paperwork.applicantsservice.models.ApplicantDTO;
import com.alm.research.gov.paperwork.applicantsservice.okta.users.OktaUsersService;
import com.alm.research.gov.paperwork.applicantsservice.repositories.ApplicantsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ApplicantsServiceImpl implements ApplicantsService {

    private final ApplicantsRepository applicantsRepository;
    private final OktaUsersService oktaUsersService;
    private final ModelMapper modelMapper;

    @Override
    public Applicant createApplicant(ApplicantDTO applicantDTO) throws IOException {
        oktaUsersService.createUser(applicantDTO);

        Applicant applicant = modelMapper.map(applicantDTO, Applicant.class);

        return applicantsRepository.save(applicant);
    }

    @Override
    public Applicant getApplicantById(String applicantId) {
        return applicantsRepository.findById(applicantId).orElseThrow(() -> {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("applicant with id %s not found", applicantId)
            );
        });
    }

    @Override
    public Applicant updateApplicant(String applicantId, ApplicantDTO applicantDTO) {
        Applicant applicant = this.getApplicantById(applicantId);

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        if (applicantDTO.getIdentity() != null) {
            modelMapper.map(applicantDTO.getIdentity(), applicant.getIdentity());
        }

        if (applicantDTO.getAddress() != null) {
            modelMapper.map(applicantDTO.getAddress(), applicant.getAddress());
        }

        modelMapper.map(applicantDTO, applicant);

        return applicantsRepository.save(applicant);
    }
}
