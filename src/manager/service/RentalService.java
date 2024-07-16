package manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RentalService<T> {
    private final List<T> items;

    public RentalService(){
        items = new ArrayList<>();
    }
    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public void forEachItem(Consumer<T> action) {
        items.forEach(action);
    }

    public void loadItems(List<T> loadedItems) {
        items.addAll(loadedItems);
    }

    public T find(Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
