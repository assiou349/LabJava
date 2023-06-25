package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.entity.Skill;
import com.labjava.skillguest.api.persistence.repository.SkillRepository;
import com.labjava.skillguest.api.service.interfaces.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;
    @Override
    public List<Skill> getSkillByJobPositionAndLevelOfExpertise(JobPosition jobPosition, LevelOfExpertise levelOfExpertise) {
        //Todo : ca ne fait pas vraiment ce qui est demandé il me semble
        //Tu ne prends pas les jobs parents et pour le niveau d'expertise tu prends ceux qui sont inférieurs
        //et pas ceux qui sont supérieurs (ou éventuellement égaux)
        return skillRepository.findAllByJobPositionAndLevelOfExpertiseLessThan(jobPosition, levelOfExpertise);
    }
}
