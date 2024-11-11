package estramipyme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserResponseDto {

    private Long id;

    @NotEmpty
    private String businessname;

    @NotEmpty
    private String docnumber;

    @NotEmpty
    private String doctype;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String persontype;

    @NotEmpty
    private String sector;

    @NotEmpty
    private String surname;

    public UserResponseDto() {
    }

    public UserResponseDto(Long id, String businessname, String docnumber, String doctype,
                   String email, String persontype, String sector, String surname) {
        this.id = id;
        this.businessname = businessname;
        this.docnumber = docnumber;
        this.doctype = doctype;
        this.email = email;
        this.persontype = persontype;
        this.sector = sector;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersontype() {
        return persontype;
    }

    public void setPersontype(String persontype) {
        this.persontype = persontype;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
