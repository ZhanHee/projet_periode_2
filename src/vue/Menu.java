package vue;


import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    // <? extends ShowInMenu> signifie "n'importe quelle classe qui implémente l'interface ShowInMenu"
    public static ShowInMenu showEntityMenu(List<? extends ShowInMenu> entities, String label){
        Scanner scanner = new Scanner(System.in);

        Integer value = null;
        System.out.println("----------------------------------");
        System.out.println(label+" : ");
        for(ShowInMenu entity : entities){
            System.out.println(entity.getIdMenu()+ " : "+entity.getLabelMenu());
        }

        while (value == null){
            try{
                System.out.print("=> ");
                String strValue = scanner.nextLine();
                if(strValue.equals("exit")){
                    return null;
                }
                value = Integer.parseInt(strValue);
                for (ShowInMenu entity : entities ){
                    if (entity.getIdMenu() == value){
                        return entity;
                    }
                }
                throw new InstanceNotFoundException();
            }catch (Exception e){
                value = null;
                System.out.println("Veuillez écrire une valeur valide.");
            }
        }
        return null;
    }

    public static Integer showGenericMenu(Map<Integer, String> options, String label){
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------------------------------");
        System.out.println(label+" :");
        for(Integer id : options.keySet()){
            System.out.println(id+ " : "+options.get(id));
        }
        Integer value = null;

        while (value == null){
            try{
                System.out.print("=> ");
                String strValue = scanner.nextLine();

                if(strValue.equals("exit")){
                    return null;
                }
                value = Integer.parseInt(strValue);

                for (Integer id : options.keySet() ){
                    if (id == value){
                        return id;
                    }
                }
                throw new InstanceNotFoundException();
            }catch (Exception e){
                value = null;
                System.out.println("Veuillez écrire une valeur valide.");
            }
        }
        return value;
    }

    public static Integer getIntegerInput(String label, Integer max, String overMaxMessage){
        Scanner scanner = new Scanner(System.in);
        Integer value = null;
        while (value == null){
            try{
                System.out.print(label+" : ");
                String strValue = scanner.nextLine();
                if(strValue.equals("exit")){
                    return null;
                }
                value = Integer.parseInt(strValue);
                if(value > max){
                    throw new IllegalArgumentException(overMaxMessage);
                }
                return value;
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("Veuillez écrire une valeur valide.");
            } finally {
                value = null;
            }
        }
        return null;
    }

}
