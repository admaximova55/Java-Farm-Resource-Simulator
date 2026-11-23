## Try a production chain

After running `bash run.sh`, enter these commands at the simulator prompt, one per line:

```text
build WheatField
build Mill
tick
info resources
graph flour
```

The wheat field produces wheat, and the mill converts it into flour. `info resources` shows the current stock, and `graph flour` shows how the flour stock has changed over time. Run `tick` a few more times, then run `graph flour` again to see the history grow.

Use `help` to list all commands or `quit` to exit.
