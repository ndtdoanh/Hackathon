package com.hacof.identity.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hacof.identity.constant.Status;
import com.hacof.identity.entity.Hackathon;
import com.hacof.identity.repository.HackathonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HackathonStatusUpdateTask {

    private final HackathonRepository hackathonRepository;

    @Scheduled(cron = "0 * * * * ?")
    public void updateHackathonStatus() {
        log.info("Checking hackathons to close...");

        List<Hackathon> hackathons =
                hackathonRepository.findByStatusAndEndDateBefore(Status.ACTIVE, LocalDateTime.now());

        for (Hackathon h : hackathons) {
            h.setStatus(Status.CLOSED);
        }

        hackathonRepository.saveAll(hackathons);

        log.info("{} hackathons updated to CLOSED", hackathons.size());
    }
}
