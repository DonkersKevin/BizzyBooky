package com.bizzybees.bizzybooky.domain;

import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    private Member member;

    @Test
    void anAdminCanRegisterANewLibrarian() {
        member = new Member("123","123","lastname","firstname","firstname@hotmail.com","street",
                "12","9000","Ghent");
        member.setRole(Role.ADMIN);
        Assertions.assertThat(member.canHaveAccessTo(Feature.REGISTER_LIBRARIAN)).isTrue();


    }
}