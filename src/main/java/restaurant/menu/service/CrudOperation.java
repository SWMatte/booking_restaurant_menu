package restaurant.menu.service;

public interface CrudOperation <T>{

    void addElement(T element) throws Exception;

    void updateElement(T element);

    void deleteElement(int id);
}
