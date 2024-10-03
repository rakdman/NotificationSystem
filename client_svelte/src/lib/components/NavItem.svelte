<script>
    export let name;
    export let href;
    const baseReference="http://localhost:5173/";
    const reference = baseReference+href;
    console.log(reference);
    console.log(name);
</script>
    <li>
        <a href={reference}>{name}</a>
    </li>
<style>
    li {
        padding: 10px;
        font-size: 20px;
        font-family: "Bahnschrift",sans-serif;
    }

    a {
        text-decoration: none;
    }

    a:hover {
        color:darkcyan;
    }
</style>