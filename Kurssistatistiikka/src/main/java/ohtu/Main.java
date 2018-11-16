package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        String coursesUrl = "https://studies.cs.helsinki.fi/courses/courseinfo";
        
        String coursesBody = Request.Get(coursesUrl).execute().returnContent().asString();
        
        Course[] courses = mapper.fromJson(coursesBody, Course[].class);
        
        JsonParser parser = new JsonParser();
        
        for (Course course : courses) {
            String statsUrl = "https://studies.cs.helsinki.fi/courses/" + course.getName() + "/stats";
            
            String statsBody = Request.Get(statsUrl).execute().returnContent().asString();
            
            JsonObject parsittuData = parser.parse(statsBody).getAsJsonObject();
            
            int submissions = 0;
            int exercises = 0;
            int hours = 0;
            
            for (Map.Entry<String, JsonElement> viikko : parsittuData.entrySet()) {
                JsonObject viikkoObject = viikko.getValue().getAsJsonObject();
                
                submissions += viikkoObject.get("students").getAsInt();
                exercises += viikkoObject.get("exercise_total").getAsInt();
                hours += viikkoObject.get("hour_total").getAsInt();
                
            }
            course.setTotalAllSubmissions(submissions);
            course.setTotalAllExercises(exercises);
            course.setTotalAllHours(hours);
            
            for (Submission sub : subs) {
                if (sub.getCourse().equals(course.getName())) {
                    course.addSubmission(sub);
                }
            }
        }
        
        System.out.println("Opiskelijanumero " + studentNr + "\n");
        
        for (Course course : courses) {
            if (course.getSubmissions().size() == 0) {
                continue;
            }
            
            System.out.println(course.getFullName() + " " + course.getTerm() + " " + course.getYear() + "\n");
            
            int tunnitYhteensa = 0;
            int tehtavatYhteensa = 0;
            int maxKurssiTehtavat = course.getMaxExercises();
            
            for (Submission sub : course.getSubmissions()) {
                int viikko = sub.getWeek();
                int tunnit = sub.getHours();
                int tehtyjaTehtavia = sub.getExercises().length;
                int maxTehtavat = course.getExercises()[viikko];
                
                tunnitYhteensa += tunnit;
                tehtavatYhteensa += tehtyjaTehtavia;
                
                StringBuilder tehdytTehtavat = new StringBuilder();
                int counter = 0;
                
                for (int t : sub.getExercises()) {
                    if (counter == 0) {
                        tehdytTehtavat.append(t);
                    } else {
                        tehdytTehtavat.append(", " + t);
                    }
                    
                    counter++;
                }
                
                System.out.println("viikko " + viikko);
                System.out.println(" tehtyjä tehtäviä " + tehtyjaTehtavia + "/" + maxTehtavat + " aikaa kului " + tunnit + " tehdyt tehtävät: " + tehdytTehtavat);
            }
            
            System.out.println("\nYhteensä " + tehtavatYhteensa + "/" + maxKurssiTehtavat + " tehtävää " + tunnitYhteensa + " tuntia\n");
            
            System.out.println("Kurssilla yhteensä " + course.getTotalAllSubmissions() + " palautusta, palautettuja tehtäviä " + course.getTotalAllExercises() + " kpl, aikaa käytetty yhteensä " + course.getTotalAllHours() + " tuntia\n");
        }
    }
}
