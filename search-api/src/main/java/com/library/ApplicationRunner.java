package com.library;

import com.library.entity.DailyStat;
import com.library.repository.DailyStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final DailyStatRepository dailyStatRepository;

    @Override
    public void run(String... args) throws Exception {
        DailyStat stat1 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat2 = new DailyStat("HTTP", LocalDateTime.now());
        DailyStat stat3 = new DailyStat("HTTP", LocalDateTime.now());

        DailyStat stat4 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat5 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat6 = new DailyStat("JAVA", LocalDateTime.now());
        DailyStat stat7 = new DailyStat("JAVA", LocalDateTime.now());

        DailyStat stat8 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat9 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat10 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat11 = new DailyStat("KOTLIN", LocalDateTime.now());
        DailyStat stat12 = new DailyStat("KOTLIN", LocalDateTime.now());

        DailyStat stat13 = new DailyStat("PYTHON", LocalDateTime.now());

        dailyStatRepository.saveAll(List.of(stat1,stat2,stat3,stat4,stat5,stat6,stat7,stat8,stat9,stat10,stat11,stat12,stat13));

    }
}
