package croundteam.cround.common.fixtures;

public final class ConstantFixtures {

    private ConstantFixtures() {}

    public static final String DEFAULT_PROFILE_IMAGE = "https://avatars.githubusercontent.com/u/132455714?s=400&u=b9befff0d433aa7f0eaf9927a4e6f55af3fe9986&v=4";
    public static final String DEFAULT_PLATFORM_URI = "https://www.youtube.com";

    public static final String CREATOR_IMAGE_PATH_PREFIX = "creator/images/";
    public static final String SHORT_FORM_IMAGE_PATH_PREFIX = "short-form/images/";

    public static final int CREATOR_TAGS_MAX_SIZE = 7;
    public static final int CREATOR_TAG_MAX_LENGTH = 20;
    public static final int CREATOR_TAGS_MIN_SIZE = 1;
    public static final int CREATOR_PLATFORM_THEME_LENGTH_MAX_SIZE = 10;

    public static final int DEFAULT_PAGE_SIZE = 5;

    public static final String PASSWORD_CHANGE_SUBJECT_MESSAGE = "[크라운드] 비밀번호 찾기 링크입니다.";
    public static final String PASSWORD_CHANGE_TEXT_PREFIX = "<h2>비밀번호 재설정 링크를 보내드립니다.</h2><br/><a href='https://cround-client.vercel.app/password/new?id=";
    public static final String PASSWORD_CHANGE_TEXT_SUFFIX = "'>\uD83D\uDC49 비밀번호 재설정하기</a><br/><br/>";

}
