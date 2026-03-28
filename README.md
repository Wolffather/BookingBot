# Booking Bot

Telegram-бот для записи клиентов к мастеру. Учебный пет-проект для освоения Kotlin, Spring Boot и разработки Telegram-ботов.

## О проекте

Бот позволяет клиентам записываться на услуги через Telegram без звонков и переписки. Пользователь выбирает услугу, мастера (опционально), дату и время — и получает подтверждение записи. Мастер получает уведомление о новой записи в свой чат.

**Что умеет бот:**
- Показывать список доступных услуг
- Выбирать мастера или записываться к любому свободному
- Выбирать дату и время из доступных слотов
- Подтверждать или отменять запись
- Показывать список активных записей (`/my_bookings`)
- Отменять существующие записи

## Стек технологий

| Слой | Технология |
|------|-----------|
| Язык | Kotlin |
| Фреймворк | Spring Boot 4 |
| База данных | PostgreSQL 14 |
| ORM | Hibernate / Spring Data JPA |
| Миграции | Flyway |
| Telegram API | telegrambots 6.9.7 |
| Контейнеризация | Docker / Docker Compose |
| Сборка | Gradle (Kotlin DSL) |

## Структура проекта

```
src/main/kotlin/
├── bot/
│   ├── BookingBot.kt              # точка входа, получает updates от Telegram
│   ├── MessageSender.kt           # отправка сообщений и клавиатур
│   ├── BotCommand.kt              # enum команд (/start, /cancel, /my_bookings)
│   ├── CallbackData.kt            # enum колбэков от инлайн-кнопок
│   ├── handler/                   # обработчики сценариев
│   │   ├── StartHandler.kt        # /start, главное меню
│   │   ├── ServiceHandler.kt      # выбор услуги
│   │   ├── MasterHandler.kt       # выбор мастера
│   │   ├── BookingHandler.kt      # выбор даты, времени, подтверждение
│   │   ├── MyBookingsHandler.kt   # список записей
│   │   └── CancelHandler.kt      # /cancel
│   ├── keyboard/                  # фабрика инлайн-клавиатур
│   ├── router/
│   │   └── UpdateRouter.kt        # маршрутизация updates по хендлерам
│   └── state/
│       ├── ConversationState.kt   # состояние диалога пользователя
│       ├── ConversationStateManager.kt
│       └── Step.kt                # enum шагов флоу
├── config/
│   └── BotConfig.kt               # регистрация бота в Telegram
├── models/                        # JPA-сущности
│   ├── Master.kt
│   ├── ServiceToBook.kt
│   ├── TimeSlot.kt
│   ├── Booking.kt
│   └── Status.kt
├── repository/                    # Spring Data репозитории
├── service/                       # бизнес-логика
│   ├── ServiceToBookService.kt
│   ├── TimeSlotService.kt
│   ├── BookingService.kt
│   └── NotificationService.kt
└── util/
    └── DateExtensions.kt          # extension-функции для форматирования дат

src/main/resources/
├── application.yml
└── db/migration/
    ├── V1__create_tables.sql
    └── V2__seed_data.sql
```

## Переменные окружения

Создай файл `.env` в корне проекта:

```env
# Telegram
TELEGRAM_BOT_TOKEN=ваш_токен_от_BotFather
TELEGRAM_BOT_USERNAME=имя_вашего_бота

# База данных
DATASOURCE_URL=jdbc:postgresql://localhost:5433/booking_bot_db
DB_USER=bot_usr
DB_PASSWORD=bot_psswrd
```

Токен получить у [@BotFather](https://t.me/BotFather) через команду `/newbot`.

## Запуск локально

**Требования:** JDK 21, Docker

**1. Клонировать репозиторий:**
```bash
git clone https://github.com/Wolffather/booking-bot.git
cd booking-bot
```

**2. Создать `.env` файл** с переменными выше.

**3. Поднять PostgreSQL:**
```bash
docker compose up -d
```

**4. Запустить приложение** через IDEA или:
```bash
./gradlew bootRun
```

Миграции Flyway выполнятся автоматически при старте — таблицы и тестовые данные создадутся сами.

## Архитектурные решения

**ConversationState** — бот хранит состояние каждого пользователя в памяти (`ConcurrentHashMap`). Это позволяет вести многошаговый диалог: бот помнит какую услугу и мастера выбрал пользователь до финального подтверждения.

**Слоистая архитектура** — `UpdateRouter` только маршрутизирует, хендлеры только обрабатывают сценарий, сервисы содержат бизнес-логику, репозитории работают с БД. Каждый слой не знает про детали соседнего.

**Flyway** запускается явно до старта Spring контекста — это обход известной проблемы с порядком инициализации бинов при использовании `telegrambots-spring-boot-starter`.