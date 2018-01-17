package com.huxley.wiisample.model.localBean;

//////////////////////////////////////////////////////////
//
//      我们的征途是星辰大海
//
//		...．．∵ ∴★．∴∵∴ ╭ ╯╭ ╯╭ ╯╭ ╯∴∵∴∵∴ 
//		．☆．∵∴∵．∴∵∴▍▍ ▍▍ ▍▍ ▍▍☆ ★∵∴ 
//		▍．∴∵∴∵．∴▅███████████☆ ★∵ 
//		◥█▅▅▅▅███▅█▅█▅█▅█▅█▅███◤ 
//		． ◥███████████████████◤
//		.．.．◥████████████████■◤
//      
//      Created by huxley on 2017/10/11.
//
//////////////////////////////////////////////////////////
public class GitHubLoginBean {

    public String[] scopes;
    public String note;
    public String note_url;
    public String client_id;
    public String client_secret;
    public String fingerprint;


    public GitHubLoginBean() {

    }

    public GitHubLoginBean(String[] scopes, String note, String note_url, String client_id, String client_secret, String fingerprint) {
        this.scopes = scopes;
        this.note = note;
        this.note_url = note_url;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.fingerprint = fingerprint;
    }
}
