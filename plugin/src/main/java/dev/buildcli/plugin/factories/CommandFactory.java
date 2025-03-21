package dev.buildcli.plugin.factories;

import dev.buildcli.plugin.BuildCLICommandPlugin;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

public class CommandFactory {
 public static CommandLine createCommandLine(BuildCLICommandPlugin plugin) {
   var parents = plugin.parents();
   var name = plugin.getClass().getDeclaredAnnotation(Command.class).name();

   if (parents == null || parents.length == 0) {
     return new CommandLine(plugin);
   }

   var spec = CommandSpec.create().name(parents[0]);
   var current = spec;
   current.mixinStandardHelpOptions(true);
   current.usageMessage().description("Command " + name + " generated by CommandFactory");

   for (var path : parents) {
     var command = CommandSpec.create().name(path);
     current.addSubcommand(command.name(), command);
     command.mixinStandardHelpOptions(true);
     command.usageMessage().description("Command " + command.name() + " generated by CommandFactory");

     current = command;
   }

   current.addSubcommand(name, new CommandLine(plugin));

   return new CommandLine(spec);
 }

}
