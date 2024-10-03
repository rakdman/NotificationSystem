<script lang="ts">
    import img from '$lib/assets/Logo.png';

</script>
    <header >
            <img src={img} alt="Logo"/>
        <title>Demo</title>
        <h1>Svelte Demo</h1>
    </header>

<style>

    img {
        max-width: 40px;
        max-height: 40px;
    }

    header {
        display: flex;
        align-items: center;
        column-gap: 10px;
    }



</style>