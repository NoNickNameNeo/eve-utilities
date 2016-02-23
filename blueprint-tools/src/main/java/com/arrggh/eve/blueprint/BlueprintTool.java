package com.arrggh.eve.blueprint;

import com.arrggh.eve.blueprint.cli.CommandLineArgumentParser;
import com.arrggh.eve.blueprint.cli.Parameters;
import com.arrggh.eve.blueprint.data.*;
import com.arrggh.eve.blueprint.locator.BlueprintLocator;
import com.arrggh.eve.blueprint.logging.LoggingUtilities;
import com.arrggh.eve.blueprint.optimizer.BlueprintOptimizer;
import com.arrggh.eve.blueprint.optimizer.BuildManifest;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;

public class BlueprintTool {
    public static void main(String[] args) throws ParseException, IOException {
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        Parameters parameters = parser.parseArguments(args);

        if (parameters.isVerbose()) {
            LoggingUtilities.setLoggingLevel(Level.ALL);
        } else if (parameters.isDebug()) {
            LoggingUtilities.setLoggingLevel(Level.INFO);
        } else {
            LoggingUtilities.setLoggingLevel(Level.WARN);
        }

        MarketPriceCache priceCache = new MarketPriceCache();
        BlueprintLoader blueprintLoader = new BlueprintLoader();
        TypeLoader typeLoader = new TypeLoader();
        PriceQuery priceQuery = new PriceQuery(priceCache);

        if (parameters.isOptimize()) {
            blueprintLoader.loadFile();
            typeLoader.loadFile();
            File priceCacheFile = new File(parameters.getPriceCache());
            MarketPriceCacheUtilities.loadCacheFromFile(priceCache, priceCacheFile);
            BlueprintOptimizer optimizer = new BlueprintOptimizer(typeLoader, blueprintLoader, priceQuery, parameters.getBlueprintName());
            BuildManifest manifest = optimizer.generateBuildTree();
            manifest.dumpToConsole();
            MarketPriceCacheUtilities.saveCacheToFile(priceCache, priceCacheFile);
        } else if (parameters.isLocate()) {
            blueprintLoader.loadFile();

            BlueprintLocator locator = new BlueprintLocator(blueprintLoader, parameters.getSearchString(), parameters.getLimit());
            locator.locate();
        } else if (parameters.isVersion()) {
            parser.dumpVersionInfoToConsole();
        } else {
            parser.dumpHelpToConsole();
        }
    }
}
