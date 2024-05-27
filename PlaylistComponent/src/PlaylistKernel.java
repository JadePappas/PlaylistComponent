import components.map.Map;
import components.standard.Standard;

/**
 * Playlist kernel component with primary methods.
 *
 * @author Jade Pappas
 *
 */
public interface PlaylistKernel
        extends Standard<Playlist>, Iterable<Map.Pair<String, String>> {
    /**
     * Adds the input song to the end of {@code this}.
     *
     * @param song
     *            the song to be added
     * @param artist
     *            the artist of the song to be added
     * @updates this
     * @ensures {@code this = #this * <song>}
     */
    void addSong(String song, String artist);

    /**
     * Removes and returns the first song from {@code this}.
     *
     * @param song
     *            the song to be removed
     * @param artist
     *            the artist of the song to be removed
     * @return the name and artist of the song removed
     * @updates this
     * @requires the song and artist are in {@code this}
     * @ensures removeSong is in #this and this = #this \ {removeSong}
     */
    Map.Pair<String, String> removeSong(String song, String Artist);

    /**
     * Reports the number of songs in {@code this}.
     *
     * @return the size of {@code this}
     * @ensures size = |this|
     */
    int size();

    /**
     * Reports the current song and artist playing in {@code this}.
     *
     * @return the current song playing in {@code this}
     * @aliases reference returned by {@code currentSong}
     * @requires {@code this /= <>} and this.playing = true
     * @ensures {@code <currentSong> is prefix of this}
     */
    Map.Pair<String, String> currentSong();

    /**
     * Renames {@code this}.
     *
     * @param name
     *            the name to rename {@code this}
     * @replaces this.name
     * @ensures this.name = name
     */
    void rename(String name);

    /**
     * Returns the name of {@code this}.
     *
     * @ensures getName = this.name
     */
    String getName();

    /**
     * Plays the current song in {@code this}.
     *
     * @requires {@code this /= <>} and this.playing = false
     * @ensures this.playing = true
     */
    void play();

    /**
     * Pauses {@code this}.
     *
     * @requires this.playing = true
     * @ensures this.playing = false
     */
    void pause();

    /**
     * Reports whether {@code this} is playing.
     *
     * @return true iff {@code this} is playing
     * @ensures isPlaying = this.playing
     */
    boolean isPlaying();
}
