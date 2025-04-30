package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role; // 권한(USER, ADMIN)

    @Embedded
    private Address address;

    private String provider;
    private String providerId;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 컬렉션은 필드에서 바로 초기화 하는 것이 안전하다.

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", provider='" + provider + '\'' +
                ", providerId='" + providerId + '\'' +
                ", orders=" + orders +                '}';
    }
}
