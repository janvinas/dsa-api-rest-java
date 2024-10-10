package com.janvinas;
import retrofit2.Retrofit;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        GithubService githubService = retrofit.create(GithubService.class);

        while(true){
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            try{
                List<Repo> repos = githubService.listRepos(username).execute().body();
                assert repos != null;
                repos.forEach(Main::printRepo);
            }catch (IOException | AssertionError e){
                System.out.println("Error executing request!");
            }
        }
    }

    private static void printRepo(Repo repo){
        System.out.println(repo.name);
        System.out.println("\t" + repo.description);
        System.out.println("\t" + repo.stargazers_count + " stargazers");
    }
}