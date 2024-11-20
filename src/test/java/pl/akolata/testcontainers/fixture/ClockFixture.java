package pl.akolata.testcontainers.fixture;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@UtilityClass
public class ClockFixture {

    public static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2024-11-19T12:00:00.00Z"), ZoneOffset.UTC);
}
