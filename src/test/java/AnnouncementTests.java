import Model.Announcement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AnnouncementTests {
    @Test
    public void equalsTest(){
        Announcement add1 = new Announcement("alxionescu",
                "accepted", "19900 euro", "Audi A6 2014",
                "description1", //Audi A6 3.0 TDI QUATTRO, 218 cp, 71744 km, Carte service\n\nTractiune 4x4 (quattro)\nAudi Drive Select : comfort, auto, dynamic, efficiency si individual\nSenzori parcare fata\/spate cu afisaj pe display + Camera\nTapiserie piele\nScaune cu memorie\nScaune fata incalzite\nFaruri Bi-Xenon\nClimatronic 4 zone\nNavigatie (lipsa card date)\nPilot automat\nSenzori ploaie si lumina\nOglinzi electrice, incalzite\nBluetooth\nStart\/Stop\n2 chei",
                "diesel", "manual",
                "71744", "2014",
                "AudiA6.jpg", "0766 094 644");
        Announcement add2 = new Announcement("alxionescu",
                "accepted", "19900 euro", "Audi A6 2014",
                "description1", //Audi A6 3.0 TDI QUATTRO, 218 cp, 71744 km, Carte service\n\nTractiune 4x4 (quattro)\nAudi Drive Select : comfort, auto, dynamic, efficiency si individual\nSenzori parcare fata\/spate cu afisaj pe display + Camera\nTapiserie piele\nScaune cu memorie\nScaune fata incalzite\nFaruri Bi-Xenon\nClimatronic 4 zone\nNavigatie (lipsa card date)\nPilot automat\nSenzori ploaie si lumina\nOglinzi electrice, incalzite\nBluetooth\nStart\/Stop\n2 chei",
                "diesel", "manual",
                "71744", "2014",
                "AudiA6.jpg", "0766 094 644");
        assertEquals(true, add1.equals(add2));
    }
}
