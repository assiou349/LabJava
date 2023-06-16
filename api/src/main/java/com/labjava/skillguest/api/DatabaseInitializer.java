package com.labjava.skillguest.api;

import com.labjava.skillguest.api.persistence.entity.*;
import com.labjava.skillguest.api.persistence.repository.*;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final InterviewRepository interviewRepository;
    private final JobPositionRepository jobPositionRepository;
    private final LineOfBusinessRepository lineOfBusinessRepository;
    private final TechnicalAdvisorRepository technicalAdvisorRepository;
    private final SkillRepository skillRepository;


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
        // Créer des instances de LineOfBusiness
        LineOfBusiness lineOfBusiness1 = new LineOfBusiness();
        lineOfBusiness1.setName("Line of Business 1");

        LineOfBusiness lineOfBusiness2 = new LineOfBusiness();
        lineOfBusiness2.setName("Line of Business 2");

        // Enregistrer les LineOfBusiness dans la base de données
        lineOfBusinessRepository.save(lineOfBusiness1);
        lineOfBusinessRepository.save(lineOfBusiness2);

        // Créer des instances de JobPosition
        JobPosition jobPosition1 = new JobPosition();
        jobPosition1.setName("Job Position 1");
        jobPosition1.setParentLineOfBusiness(lineOfBusiness1);

        JobPosition jobPosition2 = new JobPosition();
        jobPosition2.setName("Job Position 2");
        jobPosition2.setParentLineOfBusiness(lineOfBusiness2);

        // Enregistrer les JobPosition dans la base de données
        jobPositionRepository.save(jobPosition1);
        jobPositionRepository.save(jobPosition2);

        // Créer une instance de TechnicalAdvisor
        TechnicalAdvisor technicalAdvisor = new TechnicalAdvisor();
        technicalAdvisor.setName("Technical Advisor");
        technicalAdvisor.setEmail("technical.advisor@example.com");
        technicalAdvisor.setActive(true);

        // Enregistrer le TechnicalAdvisor dans la base de données
        technicalAdvisorRepository.save(technicalAdvisor);

        // Créer une instance d'Interview
        Interview interview = new Interview();
        interview.setRequesterName("Assiou");
        interview.setRequesterEmail("Assiou.t@example.com");
        interview.setPersonToMeet("Leo Messi");
        interview.setJobPosition(jobPosition1);
        interview.setLevelOfExpertise(LevelOfExpertise.A);
        interview.setDescription("Interview description");
        interview.setUrgent(true);

        // Enregistrer l'Interview dans la base de données
        interviewRepository.save(interview);

        // Créer une instance de Skill
        Skill skill = new Skill();
        skill.setTechnicalAdvisor(technicalAdvisor);
        skill.setJobPosition(jobPosition2);
        skill.setLevelOfExpertise(LevelOfExpertise.B);

        // Enregistrer le Skill dans la base de données
        skillRepository.save(skill);
    }
}
