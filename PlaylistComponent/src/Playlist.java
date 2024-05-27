import components.map.Map;

/**
 * {@code PlaylistKernel} enhanced with secondary methods.
 *
 * @author Jade Pappas
 *
 */
public interface Playlist extends PlaylistKernel {

    /**
     * Reports the song at the specified index in {@code this}.
     *
     * @param index
     *            the index to get the song in {@code this}
     * @return the song at that position
     * @aliases reference returned by {@code getSong}
     * @requires 0 <= index and index < |this|
     * @ensures <getSong> = this[index, index + 1)
     */
    Map.Pair<String, String> getSong(int index);

    /**
     * Shuffles the songs in {@code this}.
     *
     * @updates this
     * @ensures elements(this) = elements(#this)
     */
    void shuffle();

    /**
     * Skips to the next song in {@code this}.
     *
     * @ensures elements(this) = elements(#this)
     */
    void skip();

    /**
     * moves input song to the specified index in {@code this}.
     *
     * @param song
     *            the song to be moved
     * @param artist
     *            the artist of the song to be moved
     * @param index
     *            the index to move the input song
     * @updates this
     * @requires {@code this /= <>} and the input song is in {@code this} and 0
     *           <= index < this.size
     * @ensures elements(this) = elements(#this)
     */
    void moveSong(String song, String artist, int index);

}
