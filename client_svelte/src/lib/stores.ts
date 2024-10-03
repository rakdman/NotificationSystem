import { readable, type Readable, writable, type Writable} from 'svelte/store';

//TODO: Dummy auth store

export const auth : Writable<boolean> = writable(true);

