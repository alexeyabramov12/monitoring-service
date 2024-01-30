package org.example.out;


public class OutputHandler {

    public void displayHallo() {
        System.out.println("Вас приветствует сервис по приёму показаний счётчиков. \n");
        System.out.println("Если вы зарегестрированы нажмите -> 1");
        System.out.println("Если вы желаете зарегестрироваться нажмите -> 2");
    }

    public void displayMassage(String massage) {
        System.out.println(massage);
    }

    public void displayMainMenuForUser(String firstName, String lastName) {
        System.out.println("Здравствуйте ".concat(firstName).concat(" ").concat(lastName).concat("\n"));
        System.out.println("Меню нашей программы:");
        System.out.println("Для подачи показаний нажмите -> 1");
        System.out.println("Для просмотра ваших актуальные показания нажмите -> 2");
        System.out.println("Для просмотра показания за конкретный месяц нажмите -> 3");
        System.out.println("Для просмотра истории всех ваших показаний нажмите -> 4");
        System.out.println("Для жалоб и предложений нажмите -> 5");
        System.out.println("Для выхода из учетной записи нажмите -> 0");
    }

    public void displayMainMenuForAdmin(String firstName, String lastName) {
        System.out.println("Здравствуйте администратор ".concat(firstName).concat(" ").concat(lastName).concat("\n"));
        System.out.println("Меню программы:");
        System.out.println("Для подачи показаний за конкретного пользователя нажмите -> 1");
        System.out.println("Для просмотра актуальных показаний всех пользователей нажмите -> 2");
        System.out.println("Для просмотра показаний всех пользователей за конкретный месяц нажмите -> 3");
        System.out.println("Для просмотра истории показаний всех пользователей нажмите -> 4");
        System.out.println("Для просмотра жалоб и предложений нажмите -> 5");
        System.out.println("Для выхода из учетной записи нажмите -> 0");
    }



}
