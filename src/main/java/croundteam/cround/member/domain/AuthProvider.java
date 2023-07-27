package croundteam.cround.member.domain;

public enum AuthProvider {
    LOCAL, KAKAO;

    public boolean isSocial() {
        return !LOCAL.equals(this);
    }

}
