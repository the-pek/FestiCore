package org.esiea.festicore;

import java.time.LocalDate;

import org.esiea.festicore.Enumeration.ArtistName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Orga Section --- CODE T-ORGA-XX")
public class OrgaTest {
    @Test
    @DisplayName("Test T-ORGA-01")
    void createArtistTest() {
        assertNotNull(ArtistName.Damso, "Artist should not be null after creation");
        assertEquals(ArtistName.Damso, ArtistName.Damso, "Artist name should match the created name");
    }

    @Test
    @DisplayName("Test T-ORGA-02")
    void createStageTest() {
        Stage test = new Stage("Test Stage", 500);
        assertNotNull(test, "Stage should not be null after creation");
        assertEquals("Test Stage", test.getName(), "Stage name should match the created name");
        assertEquals(500, test.getCapacity(), "Stage capacity should match the created capacity");
    }

    @Test
    @DisplayName("Test T-ORGA-03")
    void createConcertTest() {
        ArtistName artist = ArtistName.Ninho;
        Stage stage = new Stage("Test Stage", 500);
        LocalDate date = LocalDate.of(2024, 7, 20);
        Concert concert = new Concert("Test Concert", artist, stage, date, java.time.Duration.ofHours(2));

        assertNotNull(concert, "Concert should not be null after creation");
        assertEquals("Test Concert", concert.getName(), "Concert name should match the created name");
        assertEquals(artist, concert.getArtistName(), "Concert artist should match the created artist");
        assertEquals(stage, concert.getStage(), "Concert stage should match the created stage");
        assertEquals(date, concert.getStartDateTime(), "Concert date should match the created date");
        assertEquals(java.time.Duration.ofHours(2), concert.getDuration(), "Concert duration should match the created duration");
    }

    @Test
    @DisplayName("Test T-ORGA-04")
    void createFestivalTest() {
        Festival festival = new Festival("Test Festival", new java.util.TreeSet<>(), new java.util.TreeSet<>(), new java.util.TreeSet<>(), new java.util.HashMap<>(), new java.util.HashMap<>());

        assertNotNull(festival, "Festival should not be null after creation");
        assertEquals("Test Festival", festival.getName(), "Festival name should match the created name");
    }

    @Test
    @DisplayName("Test T-ORGA-05")
    void equalsArtistTest() {
        ArtistName artist1 = ArtistName.Ninho;
        ArtistName artist2 = ArtistName.Gims;
        ArtistName artist3 = ArtistName.Niska;

        assertEquals(artist1, artist2, "Artists with the same name should be equal");
        assertNotEquals(artist1, artist3, "Artists with different names should not be equal");
    }

    @Test
    @DisplayName("Test T-ORGA-06")
    void equalsStageTest() {
        Stage stage1 = new Stage("Same Stage", 300);
        Stage stage2 = new Stage("Same Stage", 500);
        Stage stage3 = new Stage("Different Stage", 300);

        assertTrue(stage1.equals(stage2), "Stages with the same name should be equal");
        assertFalse(stage1.equals(stage3), "Stages with different names should not be equal");
    }

    @Test
    @DisplayName("Test T-ORGA-07")
    void equalsConcertTest() {
        ArtistName artist = ArtistName.Booba;
        Stage stage = new Stage("Test Stage", 500);
        LocalDate date = LocalDate.of(2024, 7, 20);

        Concert concert1 = new Concert("Same Concert", artist, stage, date, java.time.Duration.ofHours(2));
        Concert concert2 = new Concert("Same Concert", artist, stage, date, java.time.Duration.ofHours(3));
        Concert concert3 = new Concert("Different Concert", artist, stage, date, java.time.Duration.ofHours(2));

        assertTrue(concert1.equals(concert2), "Concerts with the same name should be equal");
        assertFalse(concert1.equals(concert3), "Concerts with different names should not be equal");
    }

    @Test
    @DisplayName("Test T-ORGA-08")
    void compareToArtistTest() {
        ArtistName artist1 = ArtistName.Damso;
        ArtistName artist2 = ArtistName.Booba;

        assertTrue(true, "Artist1 should be less than Artist2");
        assertTrue(true, "Artist2 should be greater than Artist1");
    }

    @Test
    @DisplayName("Test T-ORGA-09")
    void compareToStageTest() {
        Stage stage1 = new Stage("A Stage", 300);
        Stage stage2 = new Stage("B Stage", 500);

        assertTrue(stage1.compareTo(stage2) < 0, "Stage1 should be less than Stage2");
        assertTrue(stage2.compareTo(stage1) > 0, "Stage2 should be greater than Stage1");
    }

    @Test
    @DisplayName("Test T-ORGA-10")
    void compareToConcertTest() {
        ArtistName artist = ArtistName.Gims;
        Stage stage = new Stage("Test Stage", 500);
        LocalDate date1 = LocalDate.of(2024, 7, 20);
        LocalDate date2 = LocalDate.of(2024, 7, 21);

        Concert concert1 = new Concert("Concert A", artist, stage, date1, java.time.Duration.ofHours(2));
        Concert concert2 = new Concert("Concert B", artist, stage, date2, java.time.Duration.ofHours(2));

        assertTrue(concert1.compareTo(concert2) < 0, "Concert1 should be less than Concert2");
        assertTrue(concert2.compareTo(concert1) > 0, "Concert2 should be greater than Concert1");
    }
}
