package info.gamewise.lor.videos

import com.github.brunosc.fetcher.domain.VideoDetails
import com.github.brunosc.fetcher.domain.VideoThumbnails.ThumbnailItem
import com.github.brunosc.lor.domain.LoRRegion
import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.*
import info.gamewise.lor.videos.domain.LoRVideo
import info.gamewise.lor.videos.domain.VideoChampion
import info.gamewise.lor.videos.domain.VideoChannel
import info.gamewise.lor.videos.domain.VideoRegion
import info.gamewise.lor.videos.domain.json.Champion
import info.gamewise.lor.videos.domain.json.Channel
import info.gamewise.lor.videos.port.out.NewVideo
import java.time.LocalDate
import java.util.stream.Collectors

object DataLoader {
    const val DECK_CODE = "CICACAQDAMBQCBJHGU4AGAYFAMCAMBABAMBA6KBXAMAQCAZFAEBAGBABAMCQEAIBAEBS4"

    val MEGA_MOGWAI = Channel("MEGA_MOGWAI", "MegaMogwai", "1")
    val ALANZQ = Channel("ALANZQ", "Alanzq", "2")
    val SILVERFUSE = Channel("SILVERFUSE", "Silverfuse", "2")

    val EZREAL: Champion = Champion("01PZ036", "EZREAL", "EZREAL")
    val RENEKTON: Champion = Champion("04SH067", "RENEKTON", "RENEKTON")
    val LUCIAN: Champion = Champion("01DE022", "LUCIAN", "LUCIAN")
    val HECARIM: Champion = Champion("01SI042", "HECARIM", "HECARIM")
    val SWAIN: Champion = Champion("02NX007", "SWAIN", "SWAIN")
    val LISSANDRA: Champion = Champion("04FR005", "LISSANDRA", "LISSANDRA")
    val SHEN: Champion = Champion("01IO032", "SHEN", "SHEN")
    val JARVAN_IV: Champion = Champion("04DE008", "JARVAN_IV", "JARVAN_IV")
    val TRUNDLE: Champion = Champion("03FR006", "TRUNDLE", "TRUNDLE")
    val IRELIA: Champion = Champion("04IO005", "IRELIA", "IRELIA")
    val AZIR: Champion = Champion("04SH003", "AZIR", "AZIR")
    val ANIVIA: Champion = Champion("01FR024", "ANIVIA", "ANIVIA")
    val ELISE: Champion = Champion("01SI053", "ELISE", "ELISE")
    val KALISTA: Champion = Champion("01SI030", "KALISTA", "KALISTA")
    val TALIYAH: Champion = Champion("04SH073", "TALIYAH", "TALIYAH")
    val MALPHITE: Champion = Champion("04MT008", "MALPHITE", "MALPHITE")
    val AURELION_SOL: Champion = Champion("03MT087", "AURELION_SOL", "AURELION_SOL")
    val GAREN: Champion = Champion("01DE012", "GAREN", "GAREN")
    val TEEMO: Champion = Champion("01PZ008", "TEEMO", "TEEMO")
    val NASUS: Champion = Champion("04SH047", "NASUS", "NASUS")
    val THRESH: Champion = Champion("01SI052", "THRESH", "THRESH")
    val NAMI: Champion = Champion("05BW005", "NAMI", "NAMI")
    val TWISTED_FATE: Champion = Champion("02BW026", "TWISTED_FATE", "TWISTED_FATE")

    @JvmStatic
    fun videoDetails(id: String, deckCode: String): VideoDetails {
        return VideoDetails(playlistItem(id, deckCode, LocalDate.now().toEpochDay()))
    }

    @JvmStatic
    fun videoDetails(id: String, deckCode: String, publishedAt: Long): VideoDetails {
        return VideoDetails(playlistItem(id, deckCode, publishedAt))
    }

    fun newVideo(
        day: Int,
        id: String,
        channel: Channel,
        regions: Set<LoRRegion>,
        champions: Set<Champion>
    ): NewVideo {
        return NewVideo(
            "DECK_CODE",
            videoDetails(id, "DECK_CODE", LocalDate.of(2021, 1, day).toEpochDay()),
            channel,
            regions,
            champions
        )
    }

    @JvmStatic
    fun newLoRVideo(channel: Channel, champions: Set<Champion>, regions: Set<LoRRegion>, day: Int): LoRVideo {
        return LoRVideo(
            url = "url $day",
            title = "title $day",
            deckCode = "CODE$day",
            channel = VideoChannel(channel),
            champions = champions.stream()
                .map { champion: Champion -> VideoChampion(champion) }
                .collect(Collectors.toUnmodifiableSet()),
            regions = regions.stream().map { region: LoRRegion -> VideoRegion(region) }
                .collect(Collectors.toUnmodifiableSet()),
            publishedAt = LocalDate.of(2020, 1, day).atStartOfDay(),
            thumbnail = ThumbnailItem()
        )
    }

    private fun playlistItem(id: String, deckCode: String, publishedAt: Long): PlaylistItem {
        val thumbnail = ThumbnailDetails().setHigh(Thumbnail().setHeight(200).setWidth(200))

        val snippet: PlaylistItemSnippet = PlaylistItemSnippet()
            .setTitle("Title $id")
            .setDescription(deckCode)
            .setResourceId(ResourceId().setVideoId(id))
            .setThumbnails(thumbnail)
            .setPublishedAt(DateTime(publishedAt))
        return PlaylistItem().setSnippet(snippet)
    }
}