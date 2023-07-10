package com.labjava.skillguest.api;

import com.labjava.skillguest.api.persistence.entity.*;
import com.labjava.skillguest.api.persistence.repository.*;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final InterviewRepository interviewRepository;
    private final JobPositionRepository jobPositionRepository;
    private final LineOfBusinessRepository lineOfBusinessRepository;
    private final TechnicalAdvisorRepository technicalAdvisorRepository;
    private final SkillRepository skillRepository;
    @Autowired
    private TechnicalAdvisorInterviewRepository technicalAdvisorInterviewRepository;



    public DatabaseInitializer(InterviewRepository interviewRepository, JobPositionRepository jobPositionRepository,
                               LineOfBusinessRepository lineOfBusinessRepository,
                               TechnicalAdvisorRepository technicalAdvisorRepository, SkillRepository skillRepository) {
        this.interviewRepository = interviewRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.lineOfBusinessRepository = lineOfBusinessRepository;
        this.technicalAdvisorRepository = technicalAdvisorRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public void run(String... args) {
        generateTestData();
    }

    private void generateTestData() {
        List<LineOfBusiness> lineOfBusinessList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LineOfBusiness lineOfBusiness = new LineOfBusiness();
            lineOfBusiness.setName("LineOfBusiness " + (i + 1));
            lineOfBusiness = lineOfBusinessRepository.save(lineOfBusiness);
            lineOfBusinessList.add(lineOfBusiness);
        }

        List<JobPosition> jobPositionList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JobPosition jobPosition = new JobPosition();
            jobPosition.setName("JobPosition " + (i + 1));
            jobPosition.setParentLineOfBusiness(lineOfBusinessList.get(i));
            jobPosition = jobPositionRepository.save(jobPosition);
            jobPositionList.add(jobPosition);
        }

        List<TechnicalAdvisor> technicalAdvisorList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TechnicalAdvisor technicalAdvisor = new TechnicalAdvisor();
            technicalAdvisor.setName("TechnicalAdvisor " + (i + 1));
            technicalAdvisor.setEmail("technicalAdvisor" + (i + 1) + "@example.com");
            technicalAdvisor.setActive(true);
            technicalAdvisor = technicalAdvisorRepository.save(technicalAdvisor);
            technicalAdvisorList.add(technicalAdvisor);
        }

        List<Skill> skillList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Skill skill = new Skill();
            skill.setLevelOfExpertise(LevelOfExpertise.values()[i % LevelOfExpertise.values().length]);
            skill.setJobPosition(jobPositionList.get(i));
            skill.setTechnicalAdvisor(technicalAdvisorList.get(i));
            skill = skillRepository.save(skill);
            skillList.add(skill);
        }

        List<Interview> interviewList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Interview interview = new Interview();
            interview.setRequesterName("Requester " + (i + 1));
            interview.setRequesterEmail("requester" + (i + 1) + "@example.com");
            interview.setPersonToMeet("PersonToMeet " + (i + 1));
            interview.setJobPosition(jobPositionList.get(i));
            interview.setLevelOfExpertise(LevelOfExpertise.values()[i % LevelOfExpertise.values().length]);
            interview.setDescription("Interview description " + (i + 1));
            interview.setRequestDate(new Date());
            interview = interviewRepository.save(interview);
            interviewList.add(interview);
        }

        for (int i = 0; i < 5; i++) {
            TechnicalAdvisorInterview tai = new TechnicalAdvisorInterview();
            tai.setTechnicalAdvisor(technicalAdvisorList.get(i));
            tai.setInterview(interviewList.get(i));
            tai.setRefused(false);
            technicalAdvisorInterviewRepository.save(tai);
        }


        // Affichage des enregistrements créés
        System.out.println("Enregistrements créés avec succès!");
    }
}
