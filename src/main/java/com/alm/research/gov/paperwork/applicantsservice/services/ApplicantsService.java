package com.alm.research.gov.paperwork.applicantsservice.services;

import com.alm.research.gov.paperwork.applicantsservice.models.applicant.Applicant;
import com.alm.research.gov.paperwork.applicantsservice.models.ApplicantDTO;

import java.io.IOException;

public interface ApplicantsService {
    Applicant createApplicant(ApplicantDTO ApplicantDTO) throws IOException;
    Applicant getApplicantById(String applicantId);
    Applicant updateApplicant(String applicantId, ApplicantDTO applicantDTO);
}
