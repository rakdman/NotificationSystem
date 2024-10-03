<script>
    import NavItem from './NavItem.svelte';
    let items = [
        {name: "Instance", href:"instance" },
        {name:"Workflow", href: "workflow"}]
</script>

<nav>
    <ul>
        {#each items as i }
            <NavItem name={i.name} href="{i.href}" ></NavItem>
        {/each}
    </ul>
</nav>

<style>

    ul {
        list-style: none;
        margin-left: 10px;
    }

</style>