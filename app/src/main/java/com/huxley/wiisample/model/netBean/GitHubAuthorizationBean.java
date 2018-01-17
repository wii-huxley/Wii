package com.huxley.wiisample.model.netBean;

import java.io.Serializable;
import java.util.List;

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
//      Created by huxley on 2017/10/20.
//
//////////////////////////////////////////////////////////
public class GitHubAuthorizationBean implements Serializable {

    public int id;
    public String url;
    public GitHubAppBean app;
    public String token;
    public String hashed_token;
    public String token_last_eight;
    public String note;
    public Object note_url;
    public String created_at;
    public String updated_at;
    public Object fingerprint;
    public List<String> scopes;

}
