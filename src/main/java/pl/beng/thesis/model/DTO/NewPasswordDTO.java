package pl.beng.thesis.model.DTO;

public class NewPasswordDTO {

    private String newPassword;
    private String newPasswordConfirmed;
    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmed() {
        return newPasswordConfirmed;
    }

    public void setNewPasswordConfirmed(String newPasswordConfirmed) {
        this.newPasswordConfirmed = newPasswordConfirmed;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
