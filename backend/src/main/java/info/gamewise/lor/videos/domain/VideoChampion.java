package info.gamewise.lor.videos.domain;

import com.github.brunosc.lor.domain.LoRChampion;

public final class VideoChampion {
    private final String code;
    private final String name;
    private final String urlImgName;

    public VideoChampion(LoRChampion champion) {
        this.code = champion.getId();
        this.name = champion.prettyName();
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

    private String buildUrlImgName(LoRChampion champion) {
        if (LoRChampion.JARVAN_IV.equals(champion)) {
            return "JarvanIV";
        }
        return champion
                .prettyName()
                .replace(" ", "");
    }
}
