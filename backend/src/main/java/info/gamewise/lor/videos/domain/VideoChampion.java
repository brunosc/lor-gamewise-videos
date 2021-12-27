package info.gamewise.lor.videos.domain;

public final class VideoChampion {
    private final String code;
    private final String name;
    private final String urlImgName;

    public VideoChampion(ChampionRecord champion) {
        this.code = champion.code();
        this.name = champion.name();
        this.urlImgName = buildUrlImgName(champion);
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

    private String buildUrlImgName(ChampionRecord champion) {
        return champion
                .name()
                .replace(" ", "");
    }
}
