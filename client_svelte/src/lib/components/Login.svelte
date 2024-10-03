<script lang="ts">
    import { auth } from '../stores.ts'

    let user: string = null;
    let password: string = null;
    let loginSuccess: boolean = false;


    function login (e: Event) {
        //TODO: call api with the mentioned username and password and store the user, isAuthenticated flag, role in a store
        console.log(user);
        console.log(password);
        loginSuccess=$auth;

    }

</script>



<h1>Login</h1>

<article>
    <form class="display-vertical" on:submit ={login}>
        <label for="user">Login</label>
        <input id="user" type="text" required bind:value={user}/>
        <label for="password">Password</label>
        <input id="password" type="password" required bind:value={password}/>
        <button class="primary">Submit</button>

        {#if loginSuccess}
            <div> Login Failed </div>
        {/if}

    </form>
</article>

<style>
    /*.primary {*/
    /*    background: darkslategrey;*/
    /*    color: white;*/
    /*    border-radius: 4px;*/
    /*}*/

    /*.display-vertical {*/
    /*    display: flex;*/
    /*    flex-direction: column;*/
    /*    max-width: 300px;*/
    /*    margin: auto;*/
    /*    gap:10px ;*/
    /*}*/

    /*article {*/
    /*    text-align: center;*/
    /*}*/



</style>