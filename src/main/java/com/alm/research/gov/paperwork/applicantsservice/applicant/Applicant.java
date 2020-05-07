package com.alm.research.gov.paperwork.applicantsservice.applicant;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document(collection = "applicants")
@Data
public class Applicant implements Persistable<String> {
    @Id
    private String id;

    @NotNull
    private Address address;

    @NotNull
    private Identity identity;

    private List<com.alm.research.gov.paperwork.applicantsservice.applicant.Document> documents;

    @CreatedDate
    private Date registered_at;

    private List<Object> actions; //latest actions: like make an appointment, send docs, canceled appointment

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}