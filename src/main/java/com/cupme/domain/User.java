package com.cupme.domain;

import com.cupme.config.Constants;
import com.cupme.domain.enumeration.SexType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private SexType sex;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "size")
    private Integer size;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Address> addresses;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    public User() {}

    public User(
        Long id,
        String login,
        String password,
        String firstName,
        String lastName,
        String email,
        SexType sex,
        Integer age,
        Integer weight,
        Integer size,
        Set<Address> addresses,
        boolean activated,
        String langKey,
        String imageUrl,
        String activationKey,
        String resetKey,
        Instant resetDate,
        Set<Authority> authorities
    ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.size = size;
        this.addresses = addresses;
        this.activated = activated;
        this.langKey = langKey;
        this.imageUrl = imageUrl;
        this.activationKey = activationKey;
        this.resetKey = resetKey;
        this.resetDate = resetDate;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return (
            isActivated() == user.isActivated() &&
            getId().equals(user.getId()) &&
            getLogin().equals(user.getLogin()) &&
            getPassword().equals(user.getPassword()) &&
            getFirstName().equals(user.getFirstName()) &&
            getLastName().equals(user.getLastName()) &&
            getEmail().equals(user.getEmail()) &&
            getSex().equals(user.getSex()) &&
            getAge().equals(user.getAge()) &&
            getWeight().equals(user.getWeight()) &&
            getSize().equals(user.getSize()) &&
            Objects.equals(getAddresses(), user.getAddresses()) &&
            getLangKey().equals(user.getLangKey()) &&
            Objects.equals(getImageUrl(), user.getImageUrl()) &&
            Objects.equals(getActivationKey(), user.getActivationKey()) &&
            Objects.equals(getResetKey(), user.getResetKey()) &&
            Objects.equals(getResetDate(), user.getResetDate()) &&
            getAuthorities().equals(user.getAuthorities())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getId(),
            getLogin(),
            getPassword(),
            getFirstName(),
            getLastName(),
            getEmail(),
            getSex(),
            getAge(),
            getWeight(),
            getSize(),
            getAddresses(),
            isActivated(),
            getLangKey(),
            getImageUrl(),
            getActivationKey(),
            getResetKey(),
            getResetDate(),
            getAuthorities()
        );
    }

    @Override
    public String toString() {
        return (
            "User{" +
            "id=" +
            id +
            ", login='" +
            login +
            '\'' +
            ", password='" +
            password +
            '\'' +
            ", firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", age=" +
            age +
            ", sex=" +
            sex +
            ", weight=" +
            weight +
            ", size=" +
            size +
            ", addresses=" +
            addresses +
            ", activated=" +
            activated +
            ", langKey='" +
            langKey +
            '\'' +
            ", imageUrl='" +
            imageUrl +
            '\'' +
            ", activationKey='" +
            activationKey +
            '\'' +
            ", resetKey='" +
            resetKey +
            '\'' +
            ", resetDate=" +
            resetDate +
            ", authorities=" +
            authorities +
            '}'
        );
    }
}
