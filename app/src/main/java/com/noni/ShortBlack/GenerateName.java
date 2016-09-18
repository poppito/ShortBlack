package com.noni.ShortBlack;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateName {

    public String GenerateName() {
        String namesListB = "Jackson Aiden Liam Lucas Noah Mason Ethan Caden Jacob Logan Jayden Elijah Jack Luke Michael Benjamin Alexander James Jayce Caleb Connor William Carter Ryan Oliver Matthew Daniel Gabriel Henry Owen Grayson Dylan Landon Isaac Nicholas Wyatt Nathan Andrew Cameron Dominic Joshua Eli Sebastian Hunter Brayden David Samuel Evan Gavin Christian Max Anthony Joseph Julian John Colton Levi Muhammad Isaiah Aaron Tyler Charlie Adam Parker Austin Thomas Zachary Nolan Alex Ian Jonathan Christopher Cooper Hudson Miles Adrian Leo Blake Lincoln Jordan Tristan Jason Josiah Xavier Camden Chase Declan Carson Colin Brody Asher Jeremiah Micah Easton Xander Ryder Nathaniel Elliot Sean Cole";
        String namesListG = "Sophia Emma Olivia Ava Isabella Mia Zoe Lily Emily Madelyn Madison Chloe Charlotte Aubrey Avery Abigail Kaylee Layla Harper Ella Amelia Arianna Riley Aria Hailey Hannah Aaliyah Evelyn Addison Mackenzie Adalyn Ellie Brooklyn Nora Scarlett Grace Anna Isabelle Natalie Kaitlyn Lillian Sarah Audrey Elizabeth Leah Annabelle Kylie Mila Claire Victoria Maya Lila Elena Lucy Savannah Gabriella Callie Alaina Sophie Makayla Kennedy Sadie Skyler Allison Caroline Charlie Penelope Alyssa Peyton Samantha Liliana Bailey Maria Reagan Violet Eliana Adeline Eva Stella Keira Katherine Vivian Alice Alexandra Camilla Kayla Alexis Sydney Kaelyn Jasmine Julia Cora Lauren Piper Gianna Paisley Bella London Clara Cadence";
        ArrayList namesListBoys = new ArrayList<String>();
        ArrayList namesListGirls = new ArrayList<String>();
        WriteToFile w = new WriteToFile();


        String name = "";
        String selectionList;
        Matcher m1;
        Matcher m2;

        Pattern p = Pattern.compile("\\w+");

        m1 = p.matcher(namesListB);
        m2 = p.matcher(namesListG);


        while (m1.find()) {
            namesListBoys.add(m1.group());
        }

        while (m2.find()) {
            namesListBoys.add(m2.group());
        }

        //w.writeToFile(c, namesListBoys, namesListGirls);
        name = randomiseName(namesListBoys);

        return name;
    }


    public String randomiseName (ArrayList<String> namesList) {
        String name = "";

        Random nameGen = new Random();

        name = namesList.get(nameGen.nextInt(198));

        return name;

    }


}
