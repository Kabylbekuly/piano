import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Transposer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JSONParser parser = new JSONParser();

        // Prompt for input collection of notes
        System.out.println("Enter the JSON array of notes (e.g., [[2,1],[1,10],[1,5]]):");
        String input = scanner.nextLine();
        JSONArray notes = null;

        try {
            notes = (JSONArray) parser.parse(input);
        } catch (ParseException e) {
            System.out.println("Invalid JSON format");
            return;
        }

        // Prompt for number of semitones to transpose
        System.out.println("Enter the number of semitones to transpose (can be negative):");
        int semitones = scanner.nextInt();

        JSONArray transposedNotes = transposeNotes(notes, semitones);

        // Output result or error message
        if (transposedNotes != null) {
            System.out.println(transposedNotes);
        } else {
            System.out.println("Error: One or more transposed notes fall outside the valid range.");
        }

        scanner.close();
    }

    private static JSONArray transposeNotes(JSONArray notes, int semitones) {
        JSONArray result = new JSONArray();
        for (Object item : notes) {
            JSONArray note = (JSONArray) item;
            int octave = ((Long) note.get(0)).intValue();
            int noteNumber = ((Long) note.get(1)).intValue();

            // Calculate new note number
            int totalNotes = octave * 12 + noteNumber + semitones;
            int newOctave = totalNotes / 12;
            int newNoteNumber = totalNotes % 12;

            if (newOctave < -3 || newOctave > 5 || (newOctave == -3 && newNoteNumber < 10) || (newOctave == 5 && newNoteNumber > 1)) {
                return null; // Invalid note, return error
            }

            JSONArray transposedNote = new JSONArray();
            transposedNote.add(newOctave);
            transposedNote.add(newNoteNumber);
            result.add(transposedNote);
        }
        return result;
    }
}
