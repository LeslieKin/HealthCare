package project.healthcare.phone.db.bean;

/**
 * @author li.jq 940655212@qq.com
 *         Created on 2017/8/23.
 */

public class User {

    private String name;

    private String identity;

    private String phone;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
