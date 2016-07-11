package application;

/**
 * Created by Света on 01.07.2016.
 */


public class TestDoc {
/*
    public static void main(String[] args) throws IOException {
        //1. сохраняем данные из файлов(пока только персонал )
        //Перенести в точку входа map
        //Создаем hashmap для хранения классов и названий файлов
        Map<String,Class> staffMap = new HashMap<String,Class>();
        staffMap.put("person.xml", Persons.class);
        staffMap.put("departments.xml", Departments.class);
        staffMap.put("organizations.xml", Organizations.class);

        FileService fileService = new FileService();
         Persons person =  fileService.readFiles(staffMap);
        //создаем экземпляр DocService
        DocService docService = new DocService();
        //сохраняем person в docfieldStorage
        docService.savePersons(person);


        //доп масссив для случайной генерации одного из документов
        Class[] classDoc = new Class[3];
        classDoc[0] = Task.class;
        classDoc[1] = Outgoing.class;
        classDoc[2] = Incoming.class;
        //создаем TreeSet для хранения документов
        TreeSet<Document> allDoc = new TreeSet<Document>();
        int p;//переменная для хранения случайного значения
        //создаем документы
        for (int i = 0; i < 30; i++) {
            p = (int) (Math.random() * 3);
            Document doc = docService.createDoc(classDoc[p]);
            if (doc != null) {
                allDoc.add(doc);
            }
        }

        Map<Person, TreeSet<Document>> docsByPersonMap = new TreeMap<Person, TreeSet<Document>>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        //сортируем документы по авторам
        for (Document d : allDoc) {
            if (!docsByPersonMap.containsKey(d.getAuthor())) {
                docsByPersonMap.put(d.getAuthor(), new TreeSet<Document>());
            }
            docsByPersonMap.get(d.getAuthor()).add(d);
        }

        //генерируем отчеты по каждому автору в json файлы
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        String authorStr;
        String stringInJSON;
        String dirName = "e:\\java\\workspace\\app3\\";
        String extension = ".json";
        // сортировка документов по автору
        for (Person author : docsByPersonMap.keySet()) {
            //очищаем строку
            stringInJSON="";
            //сохраняем имя автора в переменную
            authorStr = author.getSurname() + " " + author.getName() + " " + author.getSecondName();
            //добавляем документы в формате json в строку
            for (Document d : docsByPersonMap.get(author)) {
                stringInJSON = stringInJSON + gson.toJson(d);
            }

            try (FileWriter fileWriter = new FileWriter(dirName + authorStr + extension)) {
                fileWriter.write(stringInJSON);
            } catch (IOException ex) {
                Logger.getLogger(TestDoc.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        //очищаем строку
        stringInJSON="";
        for (Person person : docService.getDocFieldsStorage().getPersonDocStorage().values()) {
            //добавляем сотрудников в формате json в строку
                stringInJSON = stringInJSON + gson.toJson(person);
        }
        try (FileWriter fileWriter = new FileWriter(dirName+"Persons"+ extension)) {
            fileWriter.write(stringInJSON);
        } catch (IOException ex) {
            Logger.getLogger(TestDoc.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }


*/
}

