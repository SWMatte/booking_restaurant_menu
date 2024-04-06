package restaurant.menu.service;

public interface CrudOperation <T>{

    void addElement(T element);

    void updateElement(T element);

    void deleteElement(int id);
}
