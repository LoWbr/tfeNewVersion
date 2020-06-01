package gradation.implementation.datatier.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PromotionRequestTest {

    @Test
    public void getId() {
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setId(1L);
        long test = 1;
        assertEquals(new Long(test), promotionRequest.getId());
    }

    @Test
    public void setId() {
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setId(1L);
        long test = 1;
        assertEquals(new Long(test), promotionRequest.getId());
        promotionRequest.setId(2L);
        test = 2;
        assertEquals(new Long(test), promotionRequest.getId());
    }

    @Test
    public void getInDemand() {
        Role role = new Role();
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setInDemand(role);
        Role role2 = role;
        assertEquals(role2, promotionRequest.getInDemand());
    }

    @Test
    public void setInDemand() {
        Role role = new Role();
        PromotionRequest promotionRequest = new PromotionRequest();
        promotionRequest.setInDemand(role);
        Role role2 = role;
        assertEquals(role2, promotionRequest.getInDemand());
    }
}