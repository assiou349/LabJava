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
        LineOfBusiness lob1 = new LineOfBusiness();
        lob1.setName("Finance");
        lineOfBusinessRepository.save(lob1);

        LineOfBusiness lob2 = new LineOfBusiness();
        lob2.setName("Informatique");
        lineOfBusinessRepository.save(lob2);

        // Création des instances de JobPosition
        JobPosition position1 = new JobPosition();
        position1.setName("Développeur principal");
        position1.setParentLineOfBusiness(lob2);
        jobPositionRepository.save(position1);

        JobPosition position2 = new JobPosition();
        position2.setName("Analyste financier");
        position2.setParentLineOfBusiness(lob1);
        jobPositionRepository.save(position2);

        // Création des instances de TechnicalAdvisor
        TechnicalAdvisor advisor1 = new TechnicalAdvisor();
        advisor1.setName("Tom Johnson");
        advisor1.setEmail("assiou349@gmail.com");
        advisor1.setActive(true);
        technicalAdvisorRepository.save(advisor1);

        TechnicalAdvisor advisor2 = new TechnicalAdvisor();
        advisor2.setName("Sarah Wilson");
        advisor2.setEmail("assiou349@gmail.com");
        advisor2.setActive(true);
        technicalAdvisorRepository.save(advisor2);

        // Création des instances de Skill
        Skill skill1 = new Skill();
        skill1.setTechnicalAdvisor(advisor1);
        skill1.setJobPosition(position1);
        skill1.setLevelOfExpertise(LevelOfExpertise.E);
        skillRepository.save(skill1);

        Skill skill2 = new Skill();
        skill2.setTechnicalAdvisor(advisor2);
        skill2.setJobPosition(position2);
        skill2.setLevelOfExpertise(LevelOfExpertise.B);
        skillRepository.save(skill2);

        // Création des instances d'Interview
        Interview interview1 = new Interview();
        interview1.setRequesterName("John Doe");
        interview1.setRequesterEmail("john.doe@example.com");
        interview1.setPersonToMeet("Jane Smith");
        interview1.setJobPosition(position1);
        interview1.setLevelOfExpertise(LevelOfExpertise.E);
        interview1.setTechnicalAdvisor(advisor1);
        interview1.setDescription("Entretien technique pour le poste de développeur principal.");
        interview1.setUrgent(false);
        interview1.setClosed(false);
        interviewRepository.save(interview1);

        Interview interview2 = new Interview();
        interview2.setRequesterName("Emily Brown");
        interview2.setRequesterEmail("em@example.com");
        interview2.setPersonToMeet("Michael Lee");
        interview2.setJobPosition(position2);
        interview2.setLevelOfExpertise(LevelOfExpertise.B);
        interview2.setTechnicalAdvisor(advisor2);
        interview2.setDescription("Entretien pour le poste d'analyste financier.");
        interview2.setUrgent(true);
        interview2.setClosed(false);
        interviewRepository.save(interview2);

        Interview interview3 = new Interview();
        interview3.setRequesterName("Alex Johnson");
        interview3.setRequesterEmail("alex.johnson@example.com");
        interview3.setPersonToMeet("David Smith");
        interview3.setJobPosition(position1);
        interview3.setLevelOfExpertise(LevelOfExpertise.A);
        interview3.setTechnicalAdvisor(advisor1);
        interview3.setDescription("Entretien pour le poste d'ingénieur logiciel junior.");
        interview3.setUrgent(false);
        interview3.setClosed(true);
        interviewRepository.save(interview3);

        Interview interview4 = new Interview();
        interview4.setRequesterName("Sarah Wilson");
        interview4.setRequesterEmail("sarah.wilson@example.com");
        interview4.setPersonToMeet("Lisa Johnson");
        interview4.setJobPosition(position2);
        interview4.setLevelOfExpertise(LevelOfExpertise.A);
        interview4.setDescription("Entretien pour le poste de responsable des ventes.");
        interview4.setUrgent(false);
        interview4.setClosed(false);
        interviewRepository.save(interview4);

        Interview interview5 = new Interview();
        interview5.setRequesterName("Michael Lee");
        interview5.setRequesterEmail("michael.lee@example.com");
        interview5.setPersonToMeet("John Doe");
        interview5.setJobPosition(position1);
        interview5.setLevelOfExpertise(LevelOfExpertise.B);
        interview5.setTechnicalAdvisor(advisor1);
        interview5.setDescription("Entretien technique pour le poste de développeur junior.");
        interview5.setUrgent(true);
        interview5.setClosed(false);
        interviewRepository.save(interview5);

        Interview interview6 = new Interview();
        interview6.setRequesterName("Jane Smith");
        interview6.setRequesterEmail("jane.smith@example.com");
        interview6.setPersonToMeet("Emily Brown");
        interview6.setJobPosition(position2);
        interview6.setLevelOfExpertise(LevelOfExpertise.E);
        interview6.setTechnicalAdvisor(advisor2);
        interview6.setDescription("Entretien pour le poste d'analyste commercial.");
        interview6.setUrgent(false);
        interview6.setClosed(false);
        interviewRepository.save(interview6);

        // Affichage des enregistrements créés
        System.out.println("Enregistrements créés avec succès!");
    }
}
