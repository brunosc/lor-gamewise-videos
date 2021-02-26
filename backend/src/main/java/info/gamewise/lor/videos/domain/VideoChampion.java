package info.gamewise.lor.videos.domain;

public final class VideoChampion {
    private final String code;
    private final String name;
    private final String urlImgName;

    public VideoChampion(String code, String name) {
        this.code = code;
        this.name = name;
        this.urlImgName = name.replace(" ", "");
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUrlImgName() {
        return urlImgName;
    }

    @Override
    public String toString() {
        return "VideoChampion{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", urlImgName='" + urlImgName + '\'' +
                '}';
    }
}
