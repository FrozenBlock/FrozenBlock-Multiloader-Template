/*
 * Copyright (C) 2024 FrozenBlock
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.ml_template;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiStatus.Internal
public final class FrozenBlockMLTemplateLogUtils {

	@ApiStatus.Internal
	public static final Logger LOGGER = LoggerFactory.getLogger(FrozenBlockMLTemplateConstants.PROJECT_ID);

	public static void log(Object string, boolean shouldLog) {
		if (shouldLog) LOGGER.info(string.toString());
	}

	public static void log(Object string) {
		log(string, true);
	}

	public static void logWarning(Object string, boolean shouldLog) {
		if (shouldLog) LOGGER.warn(string.toString());
	}

	public static void logWarning(Object string) {
		logWarning(string, true);
	}

	public static void logError(Object string, boolean shouldLog, @Nullable Throwable throwable) {
		if (shouldLog) LOGGER.error(string.toString(), throwable);
	}

	public static void logError(Object string) {
		logError(string, true, null);
	}

	public static void logError(Object string, boolean shouldLog) {
		logError(string, shouldLog, null);
	}

	public static void logError(Object string, @Nullable Throwable throwable) {
		logError(string, true, throwable);
	}


	public static void logError(Object string, @Nullable Throwable throwable, boolean shouldLog) {
		logError(string, shouldLog, throwable);
	}
}
