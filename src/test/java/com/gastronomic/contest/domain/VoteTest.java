package com.gastronomic.contest.domain;

import static com.gastronomic.contest.domain.CustomerTestSamples.*;
import static com.gastronomic.contest.domain.DishTestSamples.*;
import static com.gastronomic.contest.domain.VoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gastronomic.contest.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vote.class);
        Vote vote1 = getVoteSample1();
        Vote vote2 = new Vote();
        assertThat(vote1).isNotEqualTo(vote2);

        vote2.setId(vote1.getId());
        assertThat(vote1).isEqualTo(vote2);

        vote2 = getVoteSample2();
        assertThat(vote1).isNotEqualTo(vote2);
    }

    @Test
    void customerTest() throws Exception {
        Vote vote = getVoteRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        vote.setCustomer(customerBack);
        assertThat(vote.getCustomer()).isEqualTo(customerBack);

        vote.customer(null);
        assertThat(vote.getCustomer()).isNull();
    }

    @Test
    void dishTest() throws Exception {
        Vote vote = getVoteRandomSampleGenerator();
        Dish dishBack = getDishRandomSampleGenerator();

        vote.setDish(dishBack);
        assertThat(vote.getDish()).isEqualTo(dishBack);

        vote.dish(null);
        assertThat(vote.getDish()).isNull();
    }
}
