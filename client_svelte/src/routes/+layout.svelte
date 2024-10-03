<script lang="ts">
    import { auth } from '$lib/stores';
    import  Header  from '$lib/components/Header.svelte';
    import Login from '$lib/components/Login.svelte';
    import NavDrawer from '$lib/components/NavDrawer.svelte';
    let loginSuccess: boolean = $auth;

</script>

{#if $auth}
    <Header></Header>
    <NavDrawer></NavDrawer>
    <main>
        <slot/>
    </main>
{:else}
    <Login></Login>
{/if}



<style>

</style>