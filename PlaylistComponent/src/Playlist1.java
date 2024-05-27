import java.util.Iterator;
import java.util.NoSuchElementException;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * {@code Playlist} represented as a {@code Queue} with implementations of
 * primary methods.
 *
 * @author Jade Pappas
 *
 */
public class Playlist1 extends PlaylistSecondary {

    /*
     * Private members.
     */
    private Queue<Map.Pair<String, String>> playlist;
    private String name;
    private boolean isPlaying;

    /*
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.playlist = new Queue1L<Map.Pair<String, String>>();
        this.name = "Playlist";
        this.isPlaying = false;
    }

    /*
     * No-argument constructor.
     */
    public Playlist1() {
        this.createNewRep();
    }

    /*
     * Kernel methods.
     */
    @Override
    public void addSong(String song, String artist) {
        //create map pair and add to queue
        Map<String, String> songMap = new Map1L<>();
        songMap.add(song, artist);
        Map.Pair<String, String> addSong = songMap.remove(song);
        this.playlist.enqueue(addSong);
    }

    @Override
    public Pair<String, String> removeSong(String song, String artist) {
        //create map pair to assign song removed
        Map<String, String> songMap = new Map1L<>();
        songMap.add(song, artist);
        Map.Pair<String, String> findPair = songMap.remove(song);
        //add all other songs to queue
        int length = this.playlist.length();
        for (int i = 0; i < length; i++) {
            Map.Pair<String, String> removed = this.playlist.dequeue();
            if (!removed.key().equals(song)
                    && !removed.value().equals(artist)) {
                this.playlist.enqueue(removed);
            } else {
                findPair = removed;
            }
        }
        return findPair;
    }

    @Override
    public int size() {
        return this.playlist.length();
    }

    @Override
    public Pair<String, String> currentSong() {
        //get song at front of queue
        Map.Pair<String, String> current = this.playlist.dequeue();
        this.playlist.enqueue(current);
        //arrange queue to original order
        for (int i = 0; i < this.playlist.length() - 1; i++) {
            Map.Pair<String, String> removed = this.playlist.dequeue();
            this.playlist.enqueue(removed);
        }
        return current;
    }

    @Override
    public void rename(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void play() {
        this.isPlaying = true;
    }

    @Override
    public void pause() {
        this.isPlaying = false;
    }

    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }

    /*
     * Standard methods.
     */
    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public Playlist newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public void transferFrom(Playlist arg0) {
        assert arg0 instanceof Playlist1 : "Violation of: source is of dynamic type Playlist1()";
        Playlist1 newObj = (Playlist1) arg0;
        while (newObj.size() > 0) {
            Map.Pair<String, String> current = newObj.currentSong();
            newObj.removeSong(current.key(), current.value());
            this.addSong(current.key(), current.value());
        }
        newObj.createNewRep();
    }

    @Override
    public Iterator<Pair<String, String>> iterator() {
        return new Playlist1Iterator();
    }

    /*
     * Implementation of {@code Iterator} interface for {@code Playlist1}.
     */
    private final class Playlist1Iterator
            implements Iterator<Map.Pair<String, String>> {
        /*
         * Iterator position.
         */
        private int position;

        /*
         * Representation of iterator.
         */
        private Iterator<Map.Pair<String, String>> iterator;

        /*
         * No-argument constructor.
         */
        private Playlist1Iterator() {
            this.iterator = Playlist1.this.iterator();
            this.position = Playlist1.this.size();
        }

        @Override
        public boolean hasNext() {
            return this.position > 0;
        }

        @Override
        public Map.Pair<String, String> next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (!this.iterator.hasNext()) {
                this.iterator = Playlist1.this.iterator();
            }
            this.position--;
            return this.iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Remove operation not supported");
        }
    }
}
