import java.util.Iterator;

import components.map.Map;

/**
 * Layered implementations of secondary methods for {@code Playlist}.
 *
 * @author Jade Pappas
 *
 */
public abstract class PlaylistSecondary implements Playlist {

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Playlist)) {
            return false;
        }
        Playlist playlist = (Playlist) obj;
        if (this.size() != playlist.size()) {
            return false;
        }
        Iterator<Map.Pair<String, String>> it1 = this.iterator();
        Iterator<Map.Pair<String, String>> it2 = playlist.iterator();
        while (it1.hasNext()) {
            Map.Pair<String, String> x1 = it1.next();
            Object x2 = it2.next();
            if (!x1.equals(x2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int samples = 2;
        final int a = 37;
        final int b = 17;
        int result = 0;

        int n = 0;
        Iterator<Map.Pair<String, String>> it = this.iterator();
        while (n < samples && it.hasNext()) {
            n++;
            Map.Pair<String, String> x = it.next();
            result = a * result + b * x.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        Iterator<Map.Pair<String, String>> it = this.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }
        result.append(">");
        return result.toString();
    }

    @Override
    public Map.Pair<String, String> getSong(int index) {
        Playlist temp = this.newInstance();
        Map.Pair<String, String> returnSong = this.currentSong();
        for (int i = 0; i < this.size(); i++) {
            Map.Pair<String, String> current = this.currentSong();
            this.removeSong(current.key(), current.value());
            //return song at index
            if (i == index) {
                returnSong = current;
            }
            temp.addSong(current.key(), current.value());
        }
        this.transferFrom(temp);
        return returnSong;
    }

    @Override
    public void shuffle() {
        for (int i = 0; i < (this.size() / 3); i++) {
            Map.Pair<String, String> current = this.currentSong();
            this.removeSong(current.key(), current.value());
            this.addSong(current.key(), current.value());
        }
    }

    @Override
    public void skip() {
        Map.Pair<String, String> current = this.currentSong();
        this.removeSong(current.key(), current.value());
        this.addSong(current.key(), current.value());
    }

    @Override
    public void moveSong(String song, String artist, int index) {
        int size = this.size();
        Playlist temp = this.newInstance();
        Map.Pair<String, String> songToMove = this.currentSong();
        //find the song to move and remove it
        for (int i = 0; i < size; i++) {
            Map.Pair<String, String> current = this.currentSong();
            this.removeSong(current.key(), current.value());
            if (current.key().equals(song) && current.value().equals(artist)) {
                songToMove = current;
            } else {
                temp.addSong(current.key(), current.value());
            }
        }
        //insert the removed song to the specified index while adding songs back
        for (int i = 0; i < size; i++) {
            if (index == i) {
                this.addSong(songToMove.key(), songToMove.value());
            }
            if (temp.size() > 0) {
                Map.Pair<String, String> current = temp.currentSong();
                temp.removeSong(current.key(), current.value());
                this.addSong(current.key(), current.value());
            }
        }
    }

}
